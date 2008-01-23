import java.text.SimpleDateFormat

class BlogController {

    SearchService searchService

    def index = {redirect(action: homePage, params: params)}

    def getBlogProperty(Blog blog, String propName) {
        if (blog.blogProperties) {
            BlogProperty bp = blog.blogProperties.find {it.name == propName}
            return bp ? bp : null
        } else {
            return null
        }
    }

    def getBlogPropertyValue(Blog blog, String propName, def defValue) {

        log.debug "Searching for property ${propName} in blog ${blog.blogid}"
        BlogProperty bp = getBlogProperty(blog, propName)
        if (bp && bp.value) {
            return bp.value
        } else {
            return defValue
        }

    }

    def setBlogProperty(Blog blog, String propName, String propValue) {

        BlogProperty bp = getBlogProperty(blog, propName)
        if (bp) {
            bp.value = propValue
        } else {
            bp = new BlogProperty(name: propName, value: propValue)
            blog.addToBlogProperties(bp).save()
        }
    }

    def properties = {

        def blogId = params.blog
        log.debug "Getting Properties for ${blogId}"
        Blog blog = Blog.findByBlogid(blogId)
        BlogPropertiesCommand bpc = new BlogPropertiesCommand(id: blog.id, title: blog.title, byline: blog.byline,
                blogid: blog.blogid, allowComments: blog.allowComments)

        bpc.emailNotify = getBlogPropertyValue(blog, "emailNotify", false)
        bpc.emailAddresses = getBlogPropertyValue(blog, "emailAddresses", "")

        bpc.gtalkNotify = getBlogPropertyValue(blog, "gtalkNotify", false)
        bpc.gtalkAddresses = getBlogPropertyValue(blog, "gtalkAddresses", "")

        bpc.useFeedburner = getBlogPropertyValue(blog, "useFeedburner", false)
        bpc.fbAddress = getBlogPropertyValue(blog, "fbAddress", "")

        log.debug "Dumping cmd: " + bpc.dump()

        return [ cmd : bpc ]

    }

    def updateProperties= { BlogPropertiesCommand bpc ->

        if (bpc.hasErrors()) {
            render(view: "properties", cmd: bpc)
        } else {
            Blog blog = Blog.get(bpc.id)
            if (blog) {
                blog.title = bpc.title
                blog.byline = bpc.byline
                blog.allowComments = bpc.allowComments
                blog.blogid = bpc.blogid

                setBlogProperty(blog, "emailNotify", bpc.emailNotify.toString())
                setBlogProperty(blog, "emailAddresses", bpc.emailAddresses)

                setBlogProperty(blog, "gtalkNotify", bpc.gtalkNotify.toString())
                setBlogProperty(blog, "gtalkAddresses", bpc.gtalkAddresses)

                setBlogProperty(blog, "useFeedburner", bpc.useFeedburner.toString())
                setBlogProperty(blog, "fbAddress", bpc.fbAddress) 

                flash.message = "Successfully updated blog properties"

            } else {
                flash.message = "Could not locate blog id  [${bpc.id}] to update"

            }
            render(view: "properties", model: [ cmd: bpc ])
        }

    }

    def archive = {

        def blogId = params.blog
        log.debug "Blog id is ${blogId}"
        Blog blog = Blog.findByBlogid(blogId)
        def entries = [ ]
        def totalArchiveSize = 0
        if (blog) {
            def offset = params.offset ? Integer.parseInt(params.offset) : 0
            if (params.tagName) {
                log.debug "Looking for archive entries in category ${params.tagName}"
                def bc = BlogEntry.createCriteria()
                totalArchiveSize = bc.count {
                    eq('blog', blog)
                    eq('status', 'published')
                    tags {
                        eq('name', params.tagName)
                    }
                }
                bc = BlogEntry.createCriteria()
                entries = bc.list {
                    eq('blog', blog)
                    eq('status', 'published')
                    tags {
                        eq('name', params.tagName)
                    }
                    maxResults(20)
                    firstResult(offset)
	                order("created", "desc")
                }

            } else {
                totalArchiveSize = BlogEntry.countByBlogAndStatus(blog, "published")
                entries = BlogEntry.findAllByBlogAndStatus(blog, "published", [ sort: "created", order: "desc", offset: offset, max: 20 ] )
            }
        } else {
            flash.message = "Could not find blog archive"
        }
        def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
            grailsAttributes.getApplicationUri(request)

        return [ entries: entries, totalArchiveSize: totalArchiveSize, baseUri: baseUri, tag: params.tagName ]
        
        
    }

    def homePage = {

//        if (!request.forwardURI.endsWith("/")) {
//             redirect(uri: "/${params.blog}/")
//        }

       def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                grailsAttributes.getApplicationUri(request)


        def blogId = params.blog

        def blog = Blog.findByBlogid(blogId)
        if (blog) {
            // display most recent 5 entries
            def entries = BlogEntry.findAllByBlogAndStatus(blog, "published", [sort: 'created', order: 'desc', max: 5])
            render(view: 'displayOneEntry', model:  [blogObj: blog, entries: entries, print: params.print ? true : false, baseUri: baseUri ])
        } else {
            // flash.message = "Could not find blogid for home page" // favicon.ico hits this!
            render(view: 'displayOneEntry')
        }

    }

    def displayStaticEntry = {

        println "\n\n\nEntering STATIC ENTRY\n\n\n"

        def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                 grailsAttributes.getApplicationUri(request)

        def blogId = params.blog
        def title = params.id
        println "Looking for static entry with title [${title}]"
        def blog = Blog.findByBlogid(blogId)
        if (blog) {
            def entries = BlogEntry.findAllByBlogAndStatus(blog, "static")
            entries = entries.findAll { entry -> entry.title.encodeAsNiceTitle() == title }
            if (entries && entries.size() == 1) {
                return [ entries: entries, print: params.print ? true : false, baseUri: baseUri ]
            } else {
                response.sendError(response.SC_NOT_FOUND);
            }

        } else {
            response.sendError(response.SC_NOT_FOUND);
        }


    }

    def tagcomplete = {
        Blog blogObj = Blog.findByBlogid(params.blog)
        def queryRegex = "(?i)${params.query}" // case insensitive...
        def tags = blogObj.tags.findAll { tag -> tag.name =~ queryRegex }
        render(contentType: "text/xml") {
            results() {
                tags.each {t ->
                    result() {
                        name(t.name)
                    }
                }
            }
        }
    }

    def timeline = {
        Blog blogObj = Blog.findByBlogid(params.blog)
        def entries = BlogEntry.findAllByBlogAndStatus(blogObj, "published", [ sort: 'created', order: 'asc'])

        return [ blogObj: blogObj, startDate: entries[0]?.created  ]
    }

    def timelineData = {

        def baseUri = request.scheme + "://" + request.serverName +
                 (request.serverPort != 80 ? ":" +  request.serverPort : "") +
                 grailsAttributes.getApplicationUri(request)

        Blog blogObj = Blog.findByBlogid(params.blog)
        def entries = BlogEntry.findAllByBlogAndStatus(blogObj, "published", [ sort: 'created', order: 'asc'])

        println "Timeline rendering for ${entries.size()} entries"

        render(contentType: "text/xml") {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US)

            data() {
                entries.each { entry ->
                    event(start: sdf.format(entry.created),
                            title: entry.title,
                            link: baseUri + entry.toPermalink(),
                            "" )
	            }
            }
        }
    }

    def search = {

        def query = params.query
        def blogid = params.blog

        def baseUri = request.scheme + "://" + request.serverName +
                 (request.serverPort != 80 ? ":" +  request.serverPort : "") +
                 grailsAttributes.getApplicationUri(request)

        if (baseUri.endsWith("/"))
            baseUri =  baseUri[0..baseUri.size()-2]

        // limit query to current blog published entries...
        def results = BlogEntry.search(query, params) //   + " +blogid:${blogid}")

        return [ results: results, query: query, baseUri: baseUri ]
    }

    def searchOld = {
        def fields = params.fields
        def query = params.query
        def blogid = params.blog

        int hitcount = params.hitcount ? Integer.parseInt(params.hitcount) : 10
        int offset = params.offset ? Integer.parseInt(params.offset) : 0

        if (fields && query) {
            // limit query to current blog published entries...
            query += " AND blogid:${blogid}"

            log.debug("Field $fields with query $query with hitcount $hitcount and offset $offset")

            def fieldsList = fields.split(',')

            def results = searchService.search(query, fieldsList, hitcount, offset)

            log.debug("Total query results [" + results.totalHitCount + "]")

            return [results: results, query: query, fields: fields]

        } else {

            return [:]

        }

    }

    def displayOneEntry = {

        log.info "Ok.. We're goes to display selected entries for ${params.blog}"

        def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                grailsAttributes.getApplicationUri(request)


        def blogId = params.blog

        int year = params.year ? Integer.parseInt(params.year) : 0
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
            if (!session.account) // trim to published entries only
                entries = entries.findAll { entry -> entry.status ==~ 'published' }
            if (params.id) {
                // if we have an id, match that entry..
                def filtered = entries.findAll { it.title.encodeAsNiceTitle() == params.id }
                if (filtered.size())
                    entries = filtered
            }
            return [blog: blog, entries: entries, print: params.print ? true : false, baseUri: baseUri ]
        } else {
            response.sendError(response.SC_NOT_FOUND);
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
                log.debug "Setting type to ${params.blogType} and id to ${params.blogId} and owner ${params.account}"
                importService.setBlogId(params.blogId)
                importService.setBlogType(params.blogType)
                importService.setAccountId(params.account)
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