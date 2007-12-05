/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Nov 16, 2007
 * Time: 10:33:32 PM
 * To change this template use File | Settings | File Templates.
 */

class SecurityFilters {

    def filters = {
        loginCheck(controller: 'account', action: '*') {

            before = {
                if (!session.account) {
                    println "No user found in session"
                    redirect(controller: 'login', action: 'form')
                    return false
                } else {
                    println "Found user in session"
                    return true
                }

            }

        }
    }

}