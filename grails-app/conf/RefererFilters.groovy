/**
 * Simple filter to handle referer traffic
 */

class RefererFilters {


    def filters = {
        // do some tracking on rss feeds and 
        extractReferrers(controller:'blog', action: '*') {

            before = {

                // Referer and User-Agent and remote IP
                // action == feed?
                // println "Request is: [$request]"
                def userAgent = request.getHeader("User-Agent")
                def referer = request.getHeader("Referer")
                println "Agent: ${userAgent}, Referer: ${referer}"

                def ip = request.getRemoteAddr()

                def countryLookupService = applicationContext.getBean('countryLookupService')
                
                def country = ip ? countryLookupService.getCountryName(ip) : "N/A"
                println "Address: ${ip}, Country: ${country}"

                def url = request.getRequestURI()
                println "URL: ${url}"

                // put in the cache
                CacheService cacheService = applicationContext.getBean('cacheService')
                cacheService.putToCache("referers", 60, Calendar.getInstance(),
                                [ userAgent: userAgent, referer: referer, country: country, url: url ])


                return true

            }

        }
    }



}