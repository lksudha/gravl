/**
 * Simple filter to handle referer traffic
 */

class RefererFilters {


    def filters = {
        // do some tracking on rss feeds and 
        extractReferrers(controller:'blog', action: '*') {

            before = {

                println "Processing Referers for controller: ${controllerName} action: ${actionName}"

                def actionsToCount = [ 'homePage', 'displayOneEntry' ]

                // only look at referrers for page links, and don't count admins in stats
                if (actionsToCount.find { it == actionName } && !session.account) {

                    // Scoop up Referer and User-Agent and remote IP
                    def userAgent = request.getHeader("User-Agent")
                    def referer = request.getHeader("Referer")
                    if (referer) {
                        // strip jsessionid
                        // http://www.groovyblogs.org/entries/recent;jsessionid=29001bb1d7a8d0d55a4f13917e6a
                        referer = referer.replaceAll("(?i);?jsessionid=\\w+", "") 
                    }
                    def ip = request.getRemoteAddr()
                    println "IP: [${ip}], Referer: [${referer}], Agent: [${userAgent}]"

                    //def countryLookupService = applicationContext.getBean('countryLookupService')
                    //def country = ip ? countryLookupService.getCountryName(ip) : "N/A"

                    def url = request.forwardURI // Grails adds request.forwardURI to keep the orig URI
                    println "URL: ${url}  Referer: ${referer}"

                    // put in the cache, not use of applicationContext to lookup service bean
                    CacheService cacheService = applicationContext.getBean('cacheService')
                    cacheService.putToCache("referers", 60*60*24, Calendar.getInstance(),
                                    [ userAgent: userAgent, referer: referer, ip: ip, url: url ])
                                    
                }


                return true

            }

        }
    }



}