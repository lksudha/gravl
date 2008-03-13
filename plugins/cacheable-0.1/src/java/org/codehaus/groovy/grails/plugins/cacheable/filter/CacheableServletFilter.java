/**
 * (c) 2007 Syrics Software, Dipl.-Inf. (FH) Sven Schomaker, Softwareentwicklung
 *
 */
package org.codehaus.groovy.grails.plugins.cacheable.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.codehaus.groovy.grails.plugins.cacheable.manager.CacheableManager;
import org.codehaus.groovy.grails.web.servlet.DefaultGrailsApplicationAttributes;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;

/**
 * The CacheableServletFilter intercepts the response and
 * returns a cached version of a previous request, provided
 * the following conditions are met:
 * 
 * - the request is a GET request
 * - the request is unauthenticated
 * - the URL and QUERY_STRING matches a cached response
 * 
 * The response of a request is stored cache if the following 
 * conditions are met:
 * 
 * - the request is a GET request
 * - the request is unauthenticated
 * - the request was finished with HTTP status 200
 *   
 * @author svenschomaker
 *
 */
public class CacheableServletFilter implements Filter {

	private static final Logger log = Logger.getLogger(CacheableServletFilter.class.getName());
	private CacheableManager manager;
	private FilterConfig config;
	
	private final class CacheableServletResponse extends HttpServletResponseWrapper {
		private final ServletOutputStream sos;

		private final ByteArrayOutputStream baos;

		private int statusCode = HttpServletResponse.SC_OK;
		
		private ServletOutputStream stream;
		private PrintWriter writer;

		private CacheableServletResponse(HttpServletResponse response, 
				ServletOutputStream sos, ByteArrayOutputStream baos) {
			super(response);
			this.sos = sos;
			this.baos = baos;
		}

		public int getStatus() {
			return statusCode;
		}

		@Override
		public void sendRedirect(String url) throws IOException {
			this.statusCode = SC_FOUND;
			super.sendRedirect(url);
		}
		
		@Override
		public void sendError(int errorCode) throws IOException {
			this.statusCode = errorCode;
			super.sendError(errorCode);      
		}

		@Override
		public void sendError(int errorCode, String errorMessage) throws IOException {
		    this.statusCode = errorCode;
		    super.sendError(errorCode, errorMessage);     
		}

		@Override
		public void setStatus(int statusCode) {
		    this.statusCode = statusCode;
		    super.setStatus(statusCode);
		}

		@Override
		public ServletOutputStream getOutputStream() {
			if (writer != null) throw new IllegalStateException("getWriter already called.");
			if (stream == null) stream = new CachedOutputStream(sos, baos);
			return stream;
		}

		@Override
		public PrintWriter getWriter() {
			if (stream != null) throw new IllegalStateException("getOutputStream already called.");
			if (writer == null) writer = new PrintWriter(new CachedOutputStream(sos, baos));
			return writer;
		}
		
		@Override
		public void flushBuffer() throws IOException {
			sos.flush();
			baos.flush();
			super.flushBuffer();
		}
	}

	public void init(FilterConfig config) throws ServletException {
		log.info("Initializing filter cachingFilter");
		
		this.manager = CacheableManager.getInstance();
		this.config = config;
	}
	public void destroy() {}

	public void doFilter(ServletRequest rq, ServletResponse rs,
			FilterChain chain) throws IOException, ServletException {
		try {
			if (rq instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest)rq;
				HttpServletResponse response = (HttpServletResponse)rs;
				
				// Delegate key creation to the manager. If the key returned
				// is null, the request is not considered cacheable. The manager
				// itself will ask the controller denoted by the request for
				// a key or will generate one according to some default rules.
				Object key =  manager.createKey(new GrailsWebRequest(request, 
						response, config.getServletContext()));
				if (key != null) {
					log.fine("CacheableServletFilter: Determine the cacheLine for resource " + request.getRequestURI());
					
					Cache cache = manager.getCache();
					Element cacheLine = cache.get(key);
	
					if (cacheLine != null) {
						log.fine("CacheableServletFilter: Processing cacheLine for key " + key);
						
						try {
							// Check if the client provided any cache control 
							// headers, to obey the caching specs.
							boolean isStaleCacheLine = false;
							String cacheControl = request.getHeader("Cache-Control");
							if (cacheControl != null) {
								log.fine("CacheableServletFilter: Processing Cache-Control header " + cacheControl);
								
								// Extract the max-age from the Cache-Control header
								Pattern maxAgePattern = Pattern.compile(".*max-age=(\\d+).*");
								Matcher maxAgeMatcher = maxAgePattern.matcher(cacheControl);
								if (maxAgeMatcher.matches()) {
									// Check if the cacheLine is stale according to the 
									// specified max-age header.
									long age = cacheLine.getCreationTime();
									long maxAge = Long.parseLong(maxAgeMatcher.group(1));
									
									// Cacheline is stale according to the 
									// max-age header, so don't return the
									// cached response.
									isStaleCacheLine = (new Date().getTime()-maxAge > age);
								}
							}
							
							// Write the cached response to the client and quit
							// processing if the cacheLine is considered fresh.
							if (!isStaleCacheLine) {
								log.fine("CacheableServletFilter: Returning cached response for resource " + request.getRequestURI());
								
								Map cached = (Map) cacheLine.getObjectValue();
								response.addDateHeader("X-Cache-Date", cacheLine.getCreationTime());
								response.setContentType((String)cached.get("contentType"));
								response.setContentLength((Integer)cached.get("contentLength"));
								response.getOutputStream().write((byte[])cached.get("responseBody"));
								return;
							}
						}
						catch(Exception ex) {
							// This should only happen, if someone changed 
							// our cacheline unexpectedly or the cache is 
							// not accessible.
							log.severe("CacheableServletFilter: Caught exception while processing response returned from cache. " +
									"Replacing cache line. " + ex.getMessage());
						}
					}
					
					log.fine("CacheableServletFilter: Requesting response for resource " + request.getRequestURI());
					
					// This code is executed if either we ran into an exception or there
					// is no cached response available yet.
					// --
					// Wrap the response so we can intercept every byte that is written to
					// the response and write it into a byte array suitable for caching.
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ServletOutputStream sos = response.getOutputStream();
					CacheableServletResponse cacheable = new CacheableServletResponse(response, sos, baos);
					chain.doFilter(request, cacheable);
					cacheable.flushBuffer();
					
					// Now after we passed the wrapped response on to the chain, our
					// byte array should contain the output we want to put in cache.
					if (baos.size() > manager.getHttpResponseMinimumSize() 
							&& cacheable.getStatus() == HttpServletResponse.SC_OK) {
						log.fine("CacheableServletFilter: Caching response for resource " + request.getRequestURI());
						
						Map cached = new HashMap();
						cached.put("contentType", response.getContentType());
						cached.put("contentLength", baos.size());
						cached.put("responseBody", baos.toByteArray());
						
						manager.getCache().put(new Element(key, cached));
					}
	
					return;
				}
			}
		}
		catch(Exception ex) {
			log.severe("CacheableServletFilter: Caught exception while processing request. Passing request on " +
					"without further processing. " + ex.getMessage());
			ex.printStackTrace();
		}
		
		log.fine("CacheableServletFilter: Bypassing cache for request " + rq);
		
		// If the request is uncacheable, we leave it untouched
		// and pass it on through the chain.
		chain.doFilter(rq, rs);
	}
}
