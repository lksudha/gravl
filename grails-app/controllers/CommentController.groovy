class CommentController {

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

}

