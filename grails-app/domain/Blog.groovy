class Blog
{
  static hasMany = [ blogEntries : BlogEntry, tags : Tag, blogProperties : BlogProperty ]
  // static belongsTo = []

  static searchable = true
    
  static constraints = {
      title(nullable: false, size: 1..128)
      byline(nullable: true)
      blogid(nullable: true)
  }

  String title
  String byline
  String blogid
  boolean allowComments = true


  String toString ()
  {
    return "Blog ${id} = ${title}"
  }
}
