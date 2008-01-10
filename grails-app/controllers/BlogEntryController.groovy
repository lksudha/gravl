class BlogEntryController {


    def edit = {
        BlogEntryCommand bec = new BlogEntryCommand()
        if (params.id) {
            def blogEntry = BlogEntry.get(params.id)
            bindData(bec, blogEntry.properties)
            bec.tagList = blogEntry.tags.collect { tag -> tag.name }.join(",")
        }
        return [ blogEntry : bec ]
    }

    def preview = { BlogEntryCommand bec ->

        log.debug "Preview new entry..."
        render(template: "preview", model: [entry: bec])

    }

    def save = {  BlogEntryCommand bec ->
        BlogEntry be = new BlogEntry()
        if (bec.hasErrors()) {
            flash.message = "Errors in Blog Entry"
        } else {
            if (bec.id) {
                be = BlogEntry.get(bec.id)
            }


            bindData(be, bec.properties)
            log.debug "After bind: " + be.dump()

            //TODO: sort out tags here...
            def tags = bec.getAllTags()

            if (be.id == 0) {
                log.debug "Creating fresh Blog entry"
                be.account = session.account
                def blog = Blog.findByBlogid(params.blog)
                blog.addToBlogEntries(be).save()
                be.errors.allErrors.each {
                    println it
                }
                
            } else {
                log.debug "Saving blog entry update:" + be.validate() 
                be.save()
                be.errors.allErrors.each {
                    println it
                }
            }
            flash.message = "Successfully Updated Entry"
            redirect(uri: be.toPermalink())
        }
        redirect(uri: "/${params.blog}/admin/blog/edit")
    }

    def delete = {
        
    }

    

}