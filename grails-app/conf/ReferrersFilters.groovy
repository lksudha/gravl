/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Nov 16, 2007
 * Time: 10:33:32 PM
 * To change this template use File | Settings | File Templates.
 */

class ReferrersFilters {

    def filters = {
        extractReferrers(url: '/*') {

            before = {
                // println "Request is: [$request]"
                request.params.each { key,value ->
                    println "Next Param: ${key}=${value}"
                }
                return true

            }

        }
    }



}