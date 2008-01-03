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

            render(template: "/blog/comment", model: [comment: comment ])
            
        } else {
            Comment newComment = new Comment(comment.properties)
            BlogEntry be = BlogEntry.get(comment.entryId)
            be.addToComments(newComment).save()
            flash.message = "Successfully Added Comment"
            render("success")
            notificationService.newCommentPosted(newComment)
        }

    }

}

