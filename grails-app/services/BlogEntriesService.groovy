import net.sf.ehcache.*
import net.sf.ehcache.store.*
import org.apache.commons.logging.LogFactory

import org.codehaus.groovy.grails.commons.ConfigurationHolder


/**

	A service used to work out the a bunch of blog-entry-related facilities such as "next article" and "previous article"

*/

class BlogEntriesService {

    boolean transactional = false
    
    CacheService cacheService
    
    public def getNextPrevEntryMap(long currentEntryId) {
    	
    	def idToIds = cacheService.getFromCache("nextprev", 60, "ids")
    	if (!idToIds || idToIds.size() == 0) {
    		println "We have an empty id thingy..."
    		idToIds = [ : ]
    		BlogEntry be = BlogEntry.get(currentEntryId)
    		if (be) {
    			Blog blog = be.blog
				def entries = BlogEntry.findAllByBlogAndStatus(blog, 'published', [ sort: 'created', order: 'asc'])
				println "Iterating ${entries?.size()} entries..."
				
				0.upto(entries.size() - 1) { nextId ->
					def navMap = [ : ]
					if (nextId != 0) { // element 0 has no prev
						def prevBlog = entries.get(nextId-1)
						navMap.prevId = prevBlog.id
						navMap.prevTitle = prevBlog.title
						navMap.prevLink = prevBlog.toPermalink()
					}
					if (nextId != entries.size()-1) {
						def nextBlog = entries.get(nextId+1)
						navMap.nextId = nextBlog.id
						navMap.nextTitle = nextBlog.title
						navMap.nextLink = nextBlog.toPermalink()
					}
					idToIds[currentEntryId] = navMap
					println "Putting entry for ${entries.get(nextId).title} to point to ${navMap.prevTitle} and ${navMap.nextTitle}"
				}
    			
    		}
    		cacheService.putToCache("nextprev", 60, "ids", idToIds)
    	} 
    	def nextPrev = idToIds[currentEntryId]
    	
    }


}

