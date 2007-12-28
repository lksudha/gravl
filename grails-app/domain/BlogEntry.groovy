class BlogEntry
{

  static hasMany = [ comments : Comment, tags : Tag ]
  static belongsTo = [ Blog ]
  
  static constraints = {
      title (nullable: false, maxSize: 128)
      body (nullable: false, maxSize: 10000)
      subtitle (nullable: true, blank: true, maxSize: 128)
      excerpt (nullable:true, blank: true, maxSize: 1024)
      markup (inList: ['html', 'wiki'])
  }

  Blog blog
  Date created = new Date()
  String title
  String subtitle
  String excerpt
  String body
  String markup = "html"

  String status = "draft"
  boolean allowComments = true

  String toString ()
  {
    return "BlogEntry ${id} - ${title}"
  }

  public String toPermalink() {

      def sdf = new java.text.SimpleDateFormat("yyyy/MM/dd")

      return "/" + blog.blogid + "/" +
            sdf.format(created) + "/" +
               title.encodeAsNiceTitle() + ".html"

  }
}
