import com.sun.syndication.feed.synd.*
import com.sun.syndication.io.SyndFeedOutput

class BlogController {

    def scaffold = Blog

    CacheService cacheService
    CountryLookupService countryLookupService

    def index = {redirect(action: list, params: params)}

    def feeds = {

        log.debug "Starting feed generation..."

        def blogId = params.blog
        def category = params.categoryName
        def feedtype = params.feedtype
        
        def blog = Blog.findByBlogid(blogId)

        log.debug "Rendering feed for blog $blogId of type $feedtype"

        if (blog) {
            def entries = BlogEntry.findAllByBlogAndStatus(blog, "published", [max: 10, sort: "created", order: "desc"])
            if (category) {
                // filter to supplied category, should be done in hibernate
                entries = entries.findAll {entry ->
                    entry.tags.find {it.name == category}
                }
            }
            def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                    grailsAttributes.getApplicationUri(request)
            log.debug "BaseUri is $baseUri"

        def feedTitle = blog.title + (category ? " ($category Related category)" : "")

        def rssEntries = [ ]
        entries.each { issue ->
        	def desc = new SyndContentImpl(type: "text/html", value: blogEntry.body);
	        def entry = new SyndEntryImpl(title: blogEntry.title,
	        		link:  baseUri + blogEntry.toPermalink(),
	        		publishedDate: blogEntry.created);
	        rssEntries.add(entry);

        }
        SyndFeed feed = new SyndFeedImpl(feedType: feedtype, title: feedTitle,
        		link: baseUri + (category ? "/categories/$category" : ""),
                description: feedTitle,
        		entries: rssEntries);

        StringWriter writer = new StringWriter();
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed,writer);
        writer.close();

        return writer.toString();

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

    //TODO this should done with a closure...
    private Map toPercentMap(niceMap) {

        def percentMap = [ : ]
        def total = 0
        niceMap.values().each { value ->
            total += value
        }
        niceMap.each { key, value ->
            percentMap[key] = ((value / total) * 100).setScale(1) // one decimal for google maps
        }
        return percentMap


    }

    def stats = {

        Map allReferers = cacheService.getCacheMap("referrers")
        def hitsPerHour = [ : ]
        def browserTypes = [ : ]
        def countries = [ : ]
        def urlCount = [ : ]
        def referers = [ : ]

        allReferers.each { cal, detailsElement ->

            def details = detailsElement.getObjectValue()

            // first do hit counting
            urlCount[details.url] = urlCount[details.url] ? urlCount[details.url]+1 : 1

            // then referrer counts
            referers[details.referer] = referers[details.referer] ? referers[details.referer]+1 : 1

            // then by browser type, needs more parsing work
            // Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en-GB; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11
            browserTypes[details.userAgent] = browserTypes[details.userAgent] ? browserTypes[details.userAgent]+1 : 1

            // work out hits per hour
            def hour = cal.get(Calendar.HOUR_OF_DAY)
            hitsPerHour[hour] = hitsPerHour[hour] ? hitsPerHour[hour]+1 : 1

            // country codes from ip
            def country = details.ip ? countryLookupService.getCountryName(details.ip) : "N/A"
            countries[country] = countries[country] ? countries[country]+1 : 1

            
        }


        

        return [ urlCount: urlCount, referers: referers, browserTypes: browserTypes,
                        hitsPerHour: hitsPerHour, countries: countries,
                    browserTypesChart: toPercentMap(browserTypes),
                    countriesChart: toPercentMap(countries),
                    hitsPerHourChart: toPercentMap(hitsPerHour)
                    ]

    }

    def pendingComments = {
            
    }

    def approvedComments = {

    }

    def displayOneEntry = {

        log.info "Ok.. We're goes to display selected entries for ${params.blog}"

        def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                grailsAttributes.getApplicationUri(request)


        def blogId = params.blog

        int year = Integer.parseInt(params.year)
        int month = params.month ? Integer.parseInt(params.month) - 1 : 0
        int day = params.day ? Integer.parseInt(params.day) : 1

        log.info "Creating date query using $params.year $params.month $params.day"
        def cal = Calendar.getInstance()
        cal.set(year, month, day, 0, 0, 0)

        def blogStartDate = cal.time
        if (params.day) {
            cal.add(Calendar.HOUR_OF_DAY, 24)
        } else if (params.month) {
            cal.add(Calendar.MONTH, 1)
        } else if (params.year) {
            cal.add(Calendar.YEAR, 1)
        }
        def blogEndDate = cal.time

        log.info "Searching between $blogStartDate and $blogEndDate"

        def blog = Blog.findByBlogid(blogId)
        if (blog) {
            log.info "Blog name is ${blog.title}"
            def entries = BlogEntry.findAllByBlogAndCreatedBetween(blog, blogStartDate, blogEndDate, [sort: 'created', order: 'desc'])
            log.info "Found some entries... for $blogId then we're ${entries.size()}"
            entries = entries.findAll { entry -> entry.status ==~ 'published' }
            return [blog: blog, entries: entries, print: params.print ? true : false, baseUri: baseUri ]
        } else {
            flash.message = "Could not find blogid"
            redirect(action: list, params: params)
        }
    }

    def fileUploadFlow = {

        showDialog {
            on("upload") {
                File zipfile = File.createTempFile("gravl-import-", ".zip")
                request.getFile("zipfile").transferTo(zipfile)
                log.info "Zip file length is ${zipfile?.length()}"

                if (zipfile) {
                    log.info "Received file upload"
                    importService.setZipFile(zipfile)
                    log.info "Completed file upload"
                } else {
                    log.warn "Received an invalid file upload"
                    flash.message = "Invalid file upload"
                    return error()
                }
                return [ziptype: importService.supportedTypes]
            }.to "showImportDetails"
            on("cancel").to "headHome"
        }


        showImportDetails {
            on("cancel").to "headHome"
            on("upload") {
                log.debug "Setting type to ${params.blogType} and id to ${params.blogId}"
                importService.setBlogId(params.blogId)
                importService.setBlogType(params.blogType)
            }.to "startUpload"
        }

        startUpload {
            action {
                importService.importBlog()
                        [percentComplete: importService.percentComplete()]
            }
            on("success").to "ajaxUpload"
            // on(Exception).to "errorPage"
        }

        // display ajax upload form
        ajaxUpload {
            on("update").to("refreshResults")
            on("cancel").to "headHome"
        }

        // refresh the ajax progress
        refreshResults {
            action {
                def percent = importService.percentComplete()
                log.debug("Sending progress as: " + percent)
                        [percentComplete: percent]
            }
            on("success").to "_webflowForm"
            on("failure").to "headHome"
        }

        // send a webform with the results, unusual _ naming allows us to reuse the form
        // as a gsp template in the initial ajaxUpload view
        _webflowForm {
            on("update").to("refreshResults")
            on("complete").to("finishedImport")
            on("cancel").to "headHome"
        }

        finishedImport()


        headHome {
            redirect(action: index)
        }

        errorPage {
            redirect(action: index)
        }

    }

}