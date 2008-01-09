import com.sun.syndication.feed.synd.*
import com.sun.syndication.io.SyndFeedOutput

class FeedController {

    private boolean useFeedburner() {
		if (false) {
			def userAgent = request.getHeader("user-agent")
			if (userAgent && userAgent =~ /(?i)FeedBurner/) {
				log.info("Feedburner Agent Detected: [$userAgent]")
				return false
			} else {
				def remoteAddr = request.getRemoteAddr()
				log.debug("Redirecting: [$userAgent] from [$remoteAddr] to feedburner")
				return true
			}
		}
		return false
	}

    def feeds = {

        log.debug "Starting feed generation..."

        def supportedFormats = [ "rss_0.90", "rss_0.91", "rss_0.92", "rss_0.93", "rss_0.94", "rss_1.0", "rss_2.0", "atom_0.3", "atom_1.0"]
        def feedAbbr = [ 'rss' : 'rss_2.0', 'atom': 'atom_1.0']

        def blogId = params.blog
        def category = params.categoryName
        def feedtype = params.feedtype
        if (feedAbbr[feedtype]) { feedtype = feedAbbr[feedtype] }

        if (!supportedFormats.grep(feedtype)) {
            flash.message = "Unsupported feedtype"
            log.warn "Unsupported feed type: ${feedtype}"
            response.sendError(response.SC_FORBIDDEN);
        }


        def blog = Blog.findByBlogid(blogId)

        log.debug "Rendering feed for blog $blogId of type $feedtype"

        if (blog) {

            def entries
             if (category) {
                def bc = BlogEntry.createCriteria()
                entries = bc.list {
                    eq('status', 'published')
                    eq('blog', blog)
                    tags {
                        eq('name', category)
                    }
                    maxResults(5)
	                order("created", "desc")
	            }
             } else {
                entries = BlogEntry.findAllByBlogAndStatus(blog, "published", [max: 5, sort: "created", order: "desc"])
             }
                // filter to supplied category, should be done in hibernate
//                entries = entries.findAll {entry ->
//                    entry.tags.find {it.name == category}
//                }
            def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                    grailsAttributes.getApplicationUri(request)
            log.debug "BaseUri is $baseUri"

        def feedTitle = blog.title + (category ? " ($category Related category)" : "")

        def rssEntries = [ ]
        entries.each { blogEntry ->
        	def desc = new SyndContentImpl(type: "text/html", value: blogEntry.body);
	        def entry = new SyndEntryImpl(title: blogEntry.title,
	        		link:  baseUri + blogEntry.toPermalink(),
	        		publishedDate: blogEntry.created, description: desc)
	        rssEntries.add(entry)

        }
        SyndFeed feed = new SyndFeedImpl(feedType: feedtype, title: feedTitle,
        		link: baseUri + (category ? "/categories/$category" : ""),
                description: feedTitle,
        		entries: rssEntries)

        StringWriter writer = new StringWriter()
        SyndFeedOutput output = new SyndFeedOutput()
        output.output(feed,writer)
        writer.close()

        render(text: writer.toString(), contentType:"text/xml", encoding:"UTF-8")

//
//            def builder = new feedsplugin.FeedBuilder()
//            def feedTitle = blog.title + (category ? " ($category Related category)" : "")
//            builder.feed(title: feedTitle,
//                    link: baseUri + (category ? "/categories/$category" : ""),
//                    description: feedTitle) {
//                entries.each() {blogEntry ->
//                    entry() {
//                        title = blogEntry.title
//                        link = baseUri + blogEntry.toPermalink()
//                        publishedDate = blogEntry.created
//                        content(type: 'text/html', value: blogEntry.body) // return the content
//                    }
//                }
//            }
//
//            def romeFeed = builder.render(feedtype)
//
//            render(text: romeFeed, contentType: "text/xml", encoding: "UTF-8")

        }
    }


}