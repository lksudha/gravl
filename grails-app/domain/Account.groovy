class Account
{
  // static hasMany = []
  // static belongsTo = []
  static constraints = {
      userId (nullable: false, maxSize: 20)
      password (nullable: false)
      fullName (nullable: true)
      email (nullable: true, email: true)
  }

  String userId
  String password
  String fullName
  String email
  Date created = new Date()
  Date lastLogin = new Date()


  String toString ()
  {
    return "Account ${id}"
  }
}
