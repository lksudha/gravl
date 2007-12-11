class Comment
{
  // static hasMany = []
  static belongsTo = [ BlogEntry ]
  
  static constraints = {
      body(nullable: false, maxSize: 4096)
      author(nullable: true, blank: true)
      email(nullable: true, email: true, blank: true)
      url(nullable: true, url: true, blank: true)
      created(nullable: false)
  }

  String body
  String author
  String email
  String url
  BlogEntry blogEntry
  Date created = new Date()
  
  boolean notify = false // notify the user if other comments are added to the thread...
  String status = "pending" 

  String toString ()
  {
    return "Comment ${id}"
  }
}
