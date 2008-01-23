/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Nov 16, 2007
 * Time: 10:33:32 PM
 * To change this template use File | Settings | File Templates.
 */

class SecurityFilters {

    private String getBaseUri(request) {

        return request.scheme + "://" + request.serverName +
                    (request.serverPort != 80 ?":" + request.serverPort : "" ) +
            grailsAttributes.getApplicationUri(request)

    }

    def filters = {
        loginCheck(controller: '*', action: '*') {

            before = {

                def publicStuff = [
                                "blog" : ["archive", "homePage", "displayOneEntry", "displayStaticEntry", "search", "timeline", "timelineData"],
                                "comment" : ["newComment", "preview", "save", "opt-out"],
                                "feed": ["feeds"],
                                "pdf": ["show"],
                                "image" : ["display"],
                                "login" : ["form", "login"]
                        ]

                println ("Security checking on ${request.forwardURI}")
                if (!session.account) {
                    boolean allowed = false

                    println "Checking access to controller [${controllerName}] with action [${actionName}] (${request.forwardURI})"

                    if (publicStuff.keySet().find { it == controllerName }) {
                        
                        if (publicStuff[controllerName].find { it == actionName }) {
                            println "Access ok to public controller [${controllerName}] with action [${actionName}]"
                            if (controllerName != 'login') // keep last unsecured page in session for post-login return
                                session.returnurl = request.forwardURI
                            allowed = true
                        } else {
                            println "Access privileged action: ${actionName}"
                            allowed = false
                        }

                    } else {
                        allowed = false
                        println "Access privileged controller: ${controllerName}"

                    }

                    if (!allowed) {
                        session.returnurl = request.forwardURI
                        println "Setting return url to " + session.returnurl
                        redirect(controller: 'login', action: 'form')
                        //println "Redirecting to ${params.blogid}/login/form"
                        //redirect(url: "${params.blogid}/login/form")
                    }


                    return allowed
                } else {
                    println "Found user in session"
                    return true
                }

            }

        }
    }

}