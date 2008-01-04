class CommentController {

    NotificationService notificationService

    def newComment = {

        log.debug "Sending new comment form via Ajax"

        [ entryId: params.id ]
        // displays a new comment form
    }

    def preview = { CommentCommand comment ->

        if (comment.hasErrors())  {
           log.debug "Missing stuff in comment..."
        }
        comment.body = params.body.encodeAsWiki()

        render(template: "/blog/comment", model: [comment: comment ])
        
    }

    def save = { CommentCommand comment ->
        if (comment.hasErrors()) {
            comment.body = params.body.encodeAsWiki()
       } else {
            Comment newComment = new Comment(comment.properties)
            newComment.ipaddress = request.getRemoteAddr()
            BlogEntry be = BlogEntry.get(comment.entryId)
            be.addToComments(newComment).save()
            flash.message = "Successfully Added Comment"

            render(template: "/blog/comment", model: [comment: comment, newlySaved: true ])
            notificationService.newCommentPosted(newComment)
        }

    }

    def delete = {

        def commentId = params.id
        log.debug "Entering delete for comment ${commentId}"
        def successful
        def message
        if (Comment.exists(commentId) && session.account) {
            def comment = Comment.get(commentId)
            comment.delete()
            log.debug "Delete successful"
            message = "Successfully deleted comment"
            successful = true
        } else {
            log.debug "Delete failed"
            message = "Failed to delete message. Does not exist, or no access rights"
            successful = false
        }
        // Jetty sends back "Content-Type=text/javascript; charset=ISO-8859-1" which won't trip Prototype cause of that charset bit
        // Prototype will parse X-JSON headers if present, though, so let's do that   
        response.setHeader("X-JSON", "{'successful': ${successful}, 'commentId' : '${commentId}', 'message' : '${message}'}")
        render(contentType: "text/javascript")

    }

}

