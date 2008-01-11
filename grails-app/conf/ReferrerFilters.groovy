/**
 * Simple filter to handle referer traffic
 */

class ReferrerFilters {


    def filters = {
        // do some tracking on rss feeds and 
        extractReferrers(controller:'disabled', action: '*') {

            before = {

                // Scoop up Referer and User-Agent and remote IP
                def userAgent = request.getHeader("User-Agent")
                def referer = request.getHeader("Referer")
                def ip = request.getRemoteAddr()
                println "IP: [${ip}], Referer: [${referer}], Agent: [${userAgent}]"

                //def countryLookupService = applicationContext.getBean('countryLookupService')
                //def country = ip ? countryLookupService.getCountryName(ip) : "N/A"

                def url = request.forwardURI // Grails adds request.forwardURI to keep the orig URI
                println "URL: ${url}"

                // put in the cache, not use of applicationContext to lookup service bean
                CacheService cacheService = applicationContext.getBean('cacheService')
                cacheService.putToCache("referrers", 60*24, Calendar.getInstance(),
                                [ userAgent: userAgent, referer: referer, ip: ip, url: url ])


                return true

            }

        }
    }



}