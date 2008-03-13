/**
 * (c) 2007 Syrics Software, Dipl.-Inf. (FH) Sven Schomaker, Softwareentwicklung
 *
 */
import net.sf.ehcache.Element
import java.util.regex.Pattern
import org.codehaus.groovy.grails.plugins.cacheable.manager.CacheableManager

/**
 * Service class that helps in content caching.
 */
class CacheableService {
	
	def transactional = false
	
	CacheableManager cacheableManager
	
	/**
	 * Store an object in the named cache using the given key.
	 *
	 * @param name String naming the cache to use
	 * @param key Object to be used as the key
	 * @param val Object to be used stored under the key
	 */
	public void put(String name, Object key, Object val) {
		cacheableManager.getCache(name).put(new Element(key, val))
	}
	
	/**
	 * Store an object in the default cache using the given key.
	 *
	 * @param key Object to be used as the key
	 * @param val Object to be used stored under the key
	 */
	public void put(Object key, Object val) {
		cacheableManager.getCache().put(new Element(key, val))
	}
	
	/**
	 * Retrieve an object from the named cache using the given key.
	 * 
	 * @param name String naming the cache to use
	 * @param key Obect to be used as the key
	 * @return Returns the object stored under the given key or
	 * null if the key is not known to the cache
	 */
	public Object get(String name, Object key) {
		return cacheableManager.getCache(name).get(key)?.getObjectValue()
	}
	
	/**
	 * Retrieve an object from the default cache using the given key.
	 * 
	 * @param key Obect to be used as the key
	 * @return Returns the object stored under the given key or
	 * null if the key is not known to the cache
	 */
	public Object get(Object key) {
		return cacheableManager.getCache().get(key)?.getObjectValue()
	}
	
	/**
	 * Remove an object from the named cache using the given key.
	 * 
	 * @param name String naming the cache to use
	 * @param key Ojbect to be used as the key
	 * @return Returns the object stored under the given key or
	 * null if the key is not known to the cache
	 */
	public Object remove(String name, Object key) {
		def cache = cacheableManager.getCache()
		def val = cache.get(key)
		cache.remove(key)
		
	 	return val
	}
	
	/**
	 * Remove an object from the default cache using the given key.
	 * 
	 * @param key Ojbect to be used as the key
	 * @return Returns the object stored under the given key or
	 * null if the key is not known to the cache
	 */
	public Object remove(Object key) {
		def cache = cacheableManager.getCache()
		def val = cache.get(key)
		cache.remove(key)
		
	 	return val
	}

	/**
	 * Remove all objects from the named cache.
	 */
	public void clear(String name) {
		cacheableManager.getCache(name).removeAll()
	}
	
	/**
	 * Remove all objects from the default cache.
	 */
	public void clear() {
		cacheableManager.getCache().removeAll()
	}
}