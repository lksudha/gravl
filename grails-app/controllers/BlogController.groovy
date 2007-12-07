import au.com.bytecode.upload.BlogDataZip

class BlogController {
    def scaffold = Blog

    def index = { redirect(action:list,params:params) }

    def displayOneEntry = {

        log.info "Ok.. We're goes to display selected entries for ${params.blog}"
        
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
            def entries = BlogEntry.findAllByBlogAndCreatedBetween( blog , blogStartDate, blogEndDate, [ sort: 'created', order: 'desc'])
            log.info "Found some entries... for $blogId then we're ${entries.size()}"
            return [ blog: blog, entries: entries ]
        } else {
            flash.message = "Could not find blogid"
            redirect(action:list, params:params)
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
                return [ziptype: importService.supportedTypes ]
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
                [ percentComplete: importService.percentComplete() ]
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
                log.debug ("Sending progress as: " + percent)
                [ percentComplete: percent ]
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