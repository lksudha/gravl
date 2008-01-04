
class NotificationService {

    boolean transactional = false

    def newCommentPosted(Comment comment) {
        log.debug ("New comment posted")
    }

    def approvedComment(Comment comment) {
        log.debug ("Approved comment")
    }

}