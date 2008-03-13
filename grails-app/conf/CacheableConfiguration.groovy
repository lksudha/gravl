/**
 * This file contains some global configuration for the CacheablePlugin.
 */
class CacheableConfiguration {
	 
	 /**
	  * Defines a list of HTTP request methods that are considered cacheable
	  * by the CacheableServletFilter. If a request is made to the application
	  * with a HTTP method listed in here, the response will generally be
	  * considered cacheable and be passed to the key generator. 
	  *
	  * Use an empty list to disable HTTP result caching for your app. By default
	  * only GET requests are generally considered cacheable.
	  */
	 def httpRequestMethods = ['GET']
	    
    /**
     * This is the default request key generator closure applied to the request.
     * It is applied only, if the request passed the httpRequestMethods test and 
     * is thus generally considered cacheable. The closure is used to build a key 
     * for a request, that is used to store and retrieve a cached response from the 
     * HTTP cache. If an action or controller specific generator closure is available, 
     * the global generator is not called.
     * 
     * The closure may return any Object (most preferably a String) that identifies
     * a certain resource uniquely. If the result returned by the closure evaluates 
     * to Boolean.FALSE, the response is not cached. By default the global generator 
     * returns a String comprised of the request URI and its QUERY_STRING. 
     */
    def httpRequestKeyGenerator = { grailsWebRequest -> 
    	// def request = grailsWebRequest.currentRequest
    	// request.getRequestURI() + request.getQueryString()

    	def session = grailsWebRequest.session
    	def request = grailsWebRequest.currentRequest
		def uri = request.requestURI
		def query = request.queryString

		println "Inside cache filter for uri: [${uri}]"


		// Check for uncacheable request.
    	if (session.account) {
            println "User in session: disabling caching"
            return
        }

    	if (uri ==~ /.*(html|gif|png|jpg|mov|pdf|css|js)/) {

            println "Matched cachable URI"
            return "${uri}${query}"

        } else {

            println "Rejecting cache for uri: [${uri}]"
            return

        }

	}
    
    /** 
     * This value specifies the minimum response size in bytes to be cached. If the 
     * response is smaller than this size, the response is not cached. Beware setting
     * this size to small, will lead to also caching redirects and errors which is
     * definetely not what you will want.
     */
    def httpResponseMinimumSize = 1024 // 1kb
	    
	/**
	 * Defines configuration options for the default cache used by the CacheableServletFilter
	 * To define specific settings for a cache named "foobar", just specify a settings
	 * property called "foobarCacheSettings" as part of this file.
	 * 
	 * size - the maximum number of elements in memory, before they are evicted
	 * swap - whether to use the disk store for objects removed from memory
	 * ttl - the default amount of time to live for an element from its last accessed or modified date
	 * persist - whether to persist the cache to disk between JVM restarts
	 */
	def defaultCacheSettings = [size: 1000, swap: false, ttl: 3600, persist: false]
	 
 }