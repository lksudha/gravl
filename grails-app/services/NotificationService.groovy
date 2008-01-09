
class NotificationService {

    MailService mailService

    boolean transactional = false

    def isEmailNotifyActive(Comment comment) {

        BlogProperty bp = comment.blogEntry.blog.blogProperties?.find { prop ->
            prop.name == "emailNotify"
        }
        return bp ? bp.value : false
    }

    def sendEmailNotification(Comment comment, String address, String baseUri) {
        log.debug "Sending new notification to ${address} on comment to ${comment.blogEntry.title}"
        mailService.send(address,
                """
<p>
New Comment by ${comment.author} on
<a href='${baseUri + comment.blogEntry.toPermalink() + "#comments"}'>${comment.blogEntry.title}</a>
</p>
${comment.toMarkup()}
                """, 
                "[Gravl] New comment for ${comment.blogEntry.title} by ${comment.author}")

    }

    def emailCommentNotification(Comment comment, String baseUri) {

        BlogProperty bp = comment.blogEntry.blog.blogProperties?.find { prop ->
            prop.name == "emailAddresses"
        }
        if (bp && bp.value) {
            if (bp.value =~ /,/) {
                // send to multiple addresses (comma separated)
                bp.value.split(",").each {addr ->
                    sendEmailNotification(comment, addr, baseUri)
                }
            } else {
                sendEmailNotification(comment, bp.value, baseUri)
            }
        } else {
            log.warn "No valid email addresses available for notification"
        }

    }

    def newCommentPosted(Comment comment, String baseUri) {
        log.debug ("New comment posted")
        if (isEmailNotifyActive(comment)) {
            log.debug "Email notification active, sending comment notification"
            emailCommentNotification(comment, baseUri)
        } else {
            log.debug "Email notification inactive"
        }

    }

    def approvedComment(Comment comment, String baseUri) {
        log.debug ("Approved comment")
    }

}