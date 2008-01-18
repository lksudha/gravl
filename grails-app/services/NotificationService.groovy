
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

    def sentNewCommentOnThread(Comment comment, String address, String baseUri) {
        log.debug "Sending new thread notification to ${address} on comment to ${comment.blogEntry.title}"

        mailService.send(address,
                """

<p style="border-bottom: 1px solid black;"/>
<p>
New Comment by ${comment.author} on
<a href='${baseUri + comment.blogEntry.toPermalink() + "#comments"}'>${comment.blogEntry.title}</a>
</p>
${comment.toMarkup()}
<p style="border-bottom: 1px solid black;"/>
<p style="font-size: smaller">
When you posted a blog comment at the blog <a href='${baseUri + "/" + comment.blogEntry.blog.blogid}'>${comment.blogEntry.blog.title}</a>
 on an entry <a href='${baseUri + comment.blogEntry.toPermalink()}'>${comment.blogEntry.title}</a>, you checked a
 box marked "Email Me When New Comments Are Added". The above is the latest message on that Thread. If you're ready
to opt of any further comments on this thread,
<a href='${baseUri}/${comment.blogEntry.blog.blogid}/comment/optout?comment=${comment.id}&email=${address}'>${comment.blogEntry.blog.title}</a>  
</p>

                """,
                "[Gravl] New comment notification for ${comment.blogEntry.title} by ${comment.author}")

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

    def approvedComment(Comment newComment, String baseUri) {

        log.debug ("Approved comment")
        def allComments = newComment.blogEntry.comments
        def notify = new HashSet()
        allComments.each { c ->
            // notify everyone once, but not the current comment poster
            if (c.notify && c.email && (c.email != newComment.email)) {
                notify << c.email
            }
        }
        notify.each { email ->
            sentNewCommentOnThread(newComment, email, baseUri)
        }
    }

}