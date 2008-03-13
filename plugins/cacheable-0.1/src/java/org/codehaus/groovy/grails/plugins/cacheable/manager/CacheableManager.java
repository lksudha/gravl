/**
 * (c) 2007 Syrics Software, Dipl.-Inf. (FH) Sven Schomaker, Softwareentwicklung
 *
 */
package org.codehaus.groovy.grails.plugins.cacheable.manager;

import grails.util.GrailsUtil;
import grails.util.GrailsWebUtil;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.util.Eval;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.management.MBeanServer;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.groovy.grails.commons.ApplicationAttributes;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.web.servlet.DefaultGrailsApplicationAttributes;
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;
import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.springframework.web.servlet.mvc.Controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * The CacheableCacheManager is the central component that handles
 * the cache setup and access. It has some special hooks for the 
 * CacheableServletFilter to generate the cache key for a request.
 * 
 * @author svenschomaker
 */
public class CacheableManager {
	
	private static final Logger log = Logger.getLogger(CacheableManager.class.getName());
	
	// The name of the Cacheable cache.
	private static final String DEFAULT_CACHE_NAME = "default";
        
    // Cache configuration from CacheableConfiguration.groovy
    private Map configuration;
    
    // Default request methods considered cacheable
    private List httpRequestMethods;
    
    // Default request key generator closure.
    private Closure httpRequestKeyGenerator;
    
    // Default size of output generated from requests.
    private int httpResponseMinimumSize;
    
    // The GrailsApplication instance 
    // we use to access controllers and stuff. 
    private GrailsApplication grailsApplication;
    
    // The global CacheManager for the cache.
    private CacheManager manager = CacheManager.create();

    /*
    {
    	// Register the MBeans to allow monitoring of the cache
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(manager, mBeanServer, false, false, false, true);
    }
    */
    
    // The singleton instance.
    private static CacheableManager instance = new CacheableManager();
    private CacheableManager() {}
    
    /**
     * Return an instance of the CacheableCacheManager. This is effectively
     * a singleton instance injected on plugin setup.
     */
    public static CacheableManager getInstance() {
    	return instance;
    }
    
    /**
     * Return the default cache used by the Cacheable plugin. The default
     * cache is used by the CacheableServletFilter and get invalidated 
     * each time something changes in the DB.
     * 
     * @return the default Cache instance used in the Cacheable plugin
     */
    public Cache getCache() {
    	return getCache(DEFAULT_CACHE_NAME);
    }
    
    /**
     * Return the cache denoted by the specified name. If the cache
     * does not exists, it is created using either the default settings
     * or cache specific settings from the CacheableConfiguration.groovy
     * config class.
     * 
     * @return the default Cache instance used in the Cacheable plugin
     */
    public synchronized Cache getCache(String name) {
    	if (!manager.cacheExists(name)) {
    		Map options = getCacheSettings(name);
    		log.info("Cacheable: Setting up cache \"" + name + "\" using the following settings: " + options);
    		manager.addCache(new Cache(name, 
    				(Integer)options.get("size"), 
    				MemoryStoreEvictionPolicy.LRU,
    				(Boolean)options.get("swap"), 
    				getCacheDiskStorePath(),
    				false, 
    				(Integer)options.get("ttl"), 
    				(Integer)options.get("ttl"), 
    				(Boolean)options.get("persist"),
    				180, 
    				null));
    	}
    	return manager.getCache(name);
    }

	/**
     * Create the key for a request that is used to identify the cached resource in the
     * cache. This method delegates key generation to the controllers generator closures
     * if available. If not available, it applies some defaults to generate a key for the 
     * request.
     * 
     * @param request the HttpServletRequest identifying the resource to be cached or
     * deliverd from cache.
     * 
     * @return a String that identifies the resource delivered to the client within
     * the cache, so subsequent requests to resources identified by the same key may
     * be delivered from the cache.
     */
	public Object createKey(GrailsWebRequest request) {
		Object key = null;
		
		// Check if there are any cacheable settings defined on the controller
		// that will handle the request. If so, check these settings and generate
		// the key according to any custom generators.
		//GroovyObject controller = GrailsWebUtil.getControllerFromRequest(request.getCurrentRequest());
		//System.err.println("=======================");
		//System.err.println(request.getAttributes().getController(request.getCurrentRequest()));
		
		// Invoke the default key generator closure and check if the
		// result evaluates to false according to the "groovy truth".
		key = getHttpRequestKeyGenerator().call(request);
		if(true == (Boolean)Eval.x(key, "!x")) { 
			return null; // Null indicates no caching to the servlet.
		}
		return key; // Everything else will be used as the key.
	}
	
    // Build the settings for the specified cache or use the
    // default settings.
    private Map getCacheSettings(String name) {
    	return (Map)((Map)configuration.get("cacheSettings")).get(name);
    }

	public Closure getHttpRequestKeyGenerator() {
		return (Closure)configuration.get("httpRequestKeyGenerator");
	}
	
	public List getHttpRequestMethods() {
		return (List)configuration.get("httpRequestMethods");
	}

	public int getHttpResponseMinimumSize() {
		return (Integer)configuration.get("httpResponseMinimumSize");
	}

	public Map getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map configuration) {
		this.configuration = configuration;
	}
	
	private String getCacheDiskStorePath() {
		return System.getProperty("java.io.tmpdir") + File.separator + 
			getApplicationName() + File.separator + GrailsUtil.getEnvironment()
			+ File.separator + "cacheable";
	}
	
	private static String getApplicationName() {
        GrailsApplication app = ApplicationHolder.getApplication();
        return (String) app.getMetadata().get("app.name");
    }
}
