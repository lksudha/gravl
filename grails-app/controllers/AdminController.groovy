/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Jan 8, 2008
 * Time: 6:57:24 PM
 * To change this template use File | Settings | File Templates.
 */

class AdminController {

    CacheService cacheService
    CountryLookupService countryLookupService
    

    //TODO this should done with a closure...
    private Map toPercentMap(niceMap) {

        def percentMap = [ : ]
        def total = 0
        niceMap.values().each { value ->
            total += value
        }
        niceMap.each { key, value ->
            percentMap[key] = ((value / total) * 100).setScale(1) // one decimal for google maps
        }
        return percentMap


    }

    def stats = {

        def blogId = params.blog

        Map allReferers = cacheService.getCacheMap("referers")
        def hitsPerHour = [ : ]
        def browserTypes = [ : ]
        def countries = [ : ]
        def urlCount = [ : ]
        def referers = [ : ]

        allReferers.each { cal, detailsElement ->

            def details = detailsElement.getObjectValue()


            // first do hit counting
            urlCount[details.url] = urlCount[details.url] ? urlCount[details.url]+1 : 1

            // then referrer counts, but only for external references
            if (details.referer && (details.referer.indexOf("/${blogId}/") == -1))
                referers[details.referer] = referers[details.referer] ? referers[details.referer]+1 : 1

            // then by browser type, needs more parsing work
            // Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en-GB; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11
            browserTypes[details.userAgent] = browserTypes[details.userAgent] ? browserTypes[details.userAgent]+1 : 1

            // work out hits per hour
            def hour = cal.get(Calendar.HOUR_OF_DAY)
            hitsPerHour[hour] = hitsPerHour[hour] ? hitsPerHour[hour]+1 : 1

            // country codes from ip
            def country = details.ip ? countryLookupService.getCountryName(details.ip) : "N/A"
            countries[country] = countries[country] ? countries[country]+1 : 1


        }




        return [ urlCount: urlCount, referers: referers, browserTypes: browserTypes,
                        hitsPerHour: hitsPerHour, countries: countries,
                    browserTypesChart: toPercentMap(browserTypes),
                    countriesChart: toPercentMap(countries),
                    hitsPerHourChart: toPercentMap(hitsPerHour)
                    ]

    }

    def pendingComments = {

        def blogId = params.blog
        Blog blog = Blog.findByBlogid(blogId)
        def entries = []
        if (blog) {
            def bc = BlogEntry.createCriteria()
                entries = bc.list {
                    eq('status', 'published')
                    eq('blog', blog)
                    comments {
                        eq('status', 'pending')
                    }
	                order("created", "desc")
	            }
        } else {
            flash.message = "Could not find blog pending comments"
        }
        def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                grailsAttributes.getApplicationUri(request)

        render(view: 'comments', model: [entries: entries, baseUri: baseUri])

    }

    def approvedComments = {

    }

    def drafts = {
        def blogId = params.blog
        Blog blog = Blog.findByBlogid(blogId)
        def entries = []
        if (blog) {
            entries = BlogEntry.findAllByBlogAndStatus(blog, "unpublished", [sort: "created", order: "desc"])
        } else {
            flash.message = "Could not find blog draft entries"
        }
        def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort +
                grailsAttributes.getApplicationUri(request)

        return [entries: entries, baseUri: baseUri]

    }


}