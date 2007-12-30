
class LoginCommand {
    
   String userId
   String password
   static constraints = {
           userId(blank:false)
           password(blank:false)
   }


}