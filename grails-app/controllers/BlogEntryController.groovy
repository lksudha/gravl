class BlogEntryController {

    SearchService searchService

    def edit = {
        BlogEntryCommand bec = new BlogEntryCommand()
        if (params.id) {
            def blogEntry = BlogEntry.get(params.id)
            bindData(bec, blogEntry.properties)
            bec.id = blogEntry.id
            bec.tagList = blogEntry.tags.collect {tag -> tag.name}.join(" ")
        } else {
            bec.title = "Your Title Here"
            bec.body = "<p>\n\n</p>"
        }
        return [blogEntry: bec]
    }

    def preview = {BlogEntryCommand bec ->

        log.debug "Preview new entry..."
        render(template: "preview", model: [entry: bec])

    }

    private void fixTags(BlogEntry be, def tagList) {
        be.tags.each { tag ->
            if (tagList.find { it.equalsIgnoreCase(tag.name) }) {
                // tag already on the object... nothing to do, but mark it processed
                log.debug "Couldn't find entry in ${tagList} to match ${tag.name}"
                tagList.remove(tag.name)
            } else {
                // tag not in new list, but still on object, so remove it
                log.debug "Removing existing tag named: ${tag.name}"
                be.removeFromTags(tag)
            }
        }
        // anything still in tagList is new
        tagList.each { newTag ->
            def blogTags = Tag.findAllByBlog(be.blog)
            // do this in code so we can do case insensitive matching of tags...
            Tag newTagObj = blogTags.find { it.name.equalsIgnoreCase(newTag) }
            if (!newTagObj) {
                log.debug "Creating new tag named: ${newTag}"
                newTagObj = new Tag(name: newTag)
                newTagObj.blog = be.blog
                newTagObj.save()
                newTagObj.errors.allErrors.each {
                    println it
                }
            }
            be.addToTags(newTagObj)
        }
    }

    def save = {BlogEntryCommand bec ->
        BlogEntry be = new BlogEntry()
        if (bec.hasErrors()) {
            flash.message = "Errors in Blog Entry"
        } else {
            if (bec.id) {
                be = BlogEntry.get(bec.id)
            }

            bindData(be, bec.properties)
            log.debug "After bind: " + be.dump()

            def blog = Blog.findByBlogid(params.blog)

            if (!be.id) {
                log.debug "Creating fresh Blog entry: ${be.title}"
                be.account = session.account

                log.debug "Adding new entry to blog ${blog.title}"
                blog.addToBlogEntries(be).save()
                fixTags(be, bec.getAllTags())
                blog.errors.allErrors.each {
                    println it
                }
                //searchService.index(be)

            } else {
                log.debug "Saving blog entry update:" + be.validate()
                be.save()
                be.errors.allErrors.each {
                    println it
                }
                fixTags(be, bec.getAllTags())
                //searchService.reindex(be)
            }

            flash.message = "Successfully Updated Entry: ${be.title}"

            redirect(uri: be.toPermalink())
            return
        }
        redirect(uri: "/${params.blog}/admin/blog/edit")
    }

    def delete = {BlogEntryCommand bec ->
        log.debug "bec is " + bec.dump()
        if (bec.id) {
            BlogEntry be = BlogEntry.get(bec.id)
            be.delete()
            flash.message = "Successfully deleted entry: ${be.title}"
            // searchService.unindex(be)
        } else {
            flash.message = "Invalid blog to delete"
        }
        redirect(uri: "/${params.blog}/")
    }

}