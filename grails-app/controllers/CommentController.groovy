import org.jsecurity.*

class CommentController {

    NotificationService notificationService

    def newComment = {

        log.debug "Sending new comment form via Ajax"

        [ entryId: params.id, author: SecurityUtils.subject.authenticated ? Account.findByUserId(SecurityUtils.subject.principal).fullName : "" ]
        // displays a new comment form
    }

    def preview = { CommentCommand comment ->

        if (comment.hasErrors())  {
           log.debug "Missing stuff in comment..."
        }

        render(template: "/blog/comment", model: [comment: comment ])
        
    }

    def optout = {

        boolean success = false

        if (params.comment && params.email) {
            log.debug "Opting out on comment ${params.comment} for ${params.email}"
            Comment comment = Comment.get(params.comment)
            if (comment) {
                log.debug "Opting out on comment"
                def allComments = comment.blogEntry.comments

                allComments.each { c ->
                    if (c.email == comment.email) {
                        c.notify == false
                        c.save()
                        success = true
                    }
                }

            }
        }
        return [ success: success, email: params.email ] 

    }

    private String getBaseUri() {

        return request.scheme + "://" + request.serverName +
                    (request.serverPort != 80 ?":" + request.serverPort : "" ) +
            grailsAttributes.getApplicationUri(request)

    }

    def save = { CommentCommand comment ->
        if (comment.hasErrors()) {
            render(template: "/blog/comment", model: [comment: comment ])
       } else {
            Comment newComment = new Comment(comment.properties)
            newComment.ipaddress = request.getRemoteAddr()
            BlogEntry be = BlogEntry.get(comment.entryId)
            be.addToComments(newComment).save()

            log.debug "Rendering new comment"
            render(template: "/blog/comment", model: [comment: comment, newlySaved: true ])

            if (SecurityUtils.subject.authenticated) { // auto approve comments by owners/authenticated users
                newComment.status = "approved"
                notificationService.approvedComment(newComment, getBaseUri())
            } else {
                notificationService.newCommentPosted(newComment, getBaseUri())
            }

        }

    }

    def delete = {

        def commentId = params.id
        log.debug "Entering delete for comment ${commentId}"
        def successful
        def message
        if (Comment.exists(commentId) && SecurityUtils.subject.authenticated) {
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

    def approve = {

        def commentId = params.id
        log.debug "Entering approve for comment ${commentId}"
        def successful
        def message
        if (Comment.exists(commentId) && SecurityUtils.subject.authenticated) {
            def comment = Comment.get(commentId)
            comment.status = 'approved'
            comment.save()
            log.debug "Approval successful"
            message = "Approved comment"
            successful = true
            notificationService.approvedComment(comment, getBaseUri())
        } else {
            log.debug "Approve failed"
            message = "Failed to approve message. Does not exist, or no access rights"
            successful = false
        }
        // Jetty sends back "Content-Type=text/javascript; charset=ISO-8859-1" which won't trip Prototype cause of that charset bit
        // Prototype will parse X-JSON headers if present, though, so let's do that
        response.setHeader("X-JSON", "{'successful': ${successful}, 'commentId' : '${commentId}', 'message' : '${message}'}")
        render(contentType: "text/javascript")

    }


}

