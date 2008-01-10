class BlogEntryController {


    def edit = {
        BlogEntryCommand bec = new BlogEntryCommand()
        if (params.id) {
            def blogEntry = BlogEntry.get(params.id)
            bindData(bec, blogEntry.properties)
        }
        return [ blogEntry : bec ]
    }

    def preview = {

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
            //TODO: sort out tags here...
            def tags = bec.getAllTags()
            if (be.id == 0) {
                def blog = Blog.findByBlogid(params.blog)
                blog.addToBlogEntries(be).save()
            } else {
                be.save()
            }
            flash.message = "Successfully Updated Entry"
        }
        redirect(uri: "/${params.blog}/admin/blog/edit")
    }

    def delete = {
        
    }

    

}