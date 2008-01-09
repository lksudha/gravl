
class BlogPropertiesCommand {
    
  static constraints = {
      title(nullable: false, size: 1..128)
      byline(nullable: true)
      blogid(nullable: true)
  }

  int id // the id of the related blog domain object
  String title
  String byline
  String blogid

  boolean allowComments

  boolean emailNotify
  String emailAddresses

  boolean gtalkNotify
  String gtalkAddresses

  boolean useFeedburner
  String fbAddress

}