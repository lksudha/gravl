class BlogProperty
{
  // static hasMany = []
  static belongsTo = [ Blog ]
  // static constraints = {}

  String name
  String value
  
  Blog blog

  String toString () {
    return "BlogProperty for ${blog.blogid}: ${name} = ${value}"
  }
}
