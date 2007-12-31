
class CommentCommand {

  static constraints = {
      body(nullable: false, blank: false, maxSize: 4096)
      author(nullable: false, blank: false)
      email(nullable: true, email: true, blank: true)
      url(nullable: true, url: true, blank: true)
      emailUpdates(validator: { enabled, comment ->
        println "comment: " + comment.dump()
        if (enabled &&  !comment.email ) {
            println "No email address.. but updates checked"
            return false
        }
      })
  }

  String body
  String author
  String email
  String url
  boolean emailUpdates
  Date created = new Date()

}