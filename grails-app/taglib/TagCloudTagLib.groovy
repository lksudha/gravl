class TagCloudTagLib {

    // Simple Algo from http://www.petefreitag.com/item/396.cfm
    def tagCloud = {attrs, body ->

        Blog.withTransaction { stats ->
        def blogId = attrs.blogId
        if (!blogId)
            return
            
        Blog blog = Blog.findByBlogid(blogId)
        if (!blog)
            return;
            
        log.debug "Building tagcloud for: $blog.title"

        // step 1: determine tags and their frequency
        def tagToFreq = new TreeMap()

        blog.tags.each {tag ->
            def tagCount = tag.entries.size()
            tagToFreq[tag.name] = tagCount
            // log.debug "Setting size of ${tag.name} to $tagCount"
        }
        def allFreq = tagToFreq.collect {tag, freq -> return freq}.sort()
        def minFreq = allFreq[0]  // first entry
        def maxFreq = allFreq[allFreq.size() > 1 ? -1 : 0] // last entry
        log.debug "Max is $maxFreq and min is $minFreq"

        // step 3: find the spread, use 5 font sizes
        def distrib = maxFreq - minFreq
        def catSize = (distrib / 5).intValue() + 1 // add 1 to simulate ceil()
        log.debug "Catsize is $catSize"


        // step 4: output your tags
        tagToFreq.each {tag, freq ->
            out << "<a href='${request.contextPath}/$blogId/archive/$tag' class='tagCloudSize" + (freq / catSize).intValue() + "'>"
            out << tag
            out << "</a> "
            // log.debug "Tag class of $tag with size $freq is " +  (freq / catSize).intValue()
        }
        }

        
    }

}