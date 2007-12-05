/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Nov 16, 2007
 * Time: 10:53:32 PM
 * To change this template use File | Settings | File Templates.
 */

class LoginCommand {
    
   String userId
   String password
   static constraints = {
           userId(blank:false)
           password(blank:false)
   }


}