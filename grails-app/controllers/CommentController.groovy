class CommentController {

    def newComment = {
        log.debug "Sending new comment form via Ajax"

        [ entryId: params.id ]
        // displays a new comment form
    }

    def preview = {

        Comment comment = new Comment()
        comment.properties = params
        comment.body = params.body.encodeAsWiki()
        log.debug "New comment is ${comment.dump()}"

        render(template: "/blog/comment", model: [comment: comment])
        
    }

}