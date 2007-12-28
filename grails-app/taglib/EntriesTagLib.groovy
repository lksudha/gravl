import org.codehaus.groovy.grails.commons.ConfigurationHolder

class EntriesTagLib {

    CacheService cacheService

    public static String getNiceDate(Date date) {

        def now = new Date()

        def diff = Math.abs(now.getTime() - date.getTime())

        long second = 1000
        long minute = 1000 * 60
        long hour = minute * 60
        long day = hour * 24

        def niceTime = ""

        long calc = 0L;

        calc = Math.floor(diff / day)
        if (calc > 0) {
            niceTime += calc + " day" + (calc > 1 ? "s " : " ")
            diff = diff % day
        }

        calc = Math.floor(diff / hour)
        if (calc > 0) {
            niceTime += calc + " hour" + (calc > 1 ? "s " : " ")
            diff = diff % hour
        }

        calc = Math.floor(diff / minute)
        if (calc > 0) {
            niceTime += calc + " minute" + (calc > 1 ? "s " : " ")
            diff = diff % minute
        }

        if (niceTime.length() == 0) {
            niceTime = "Right now"
        } else {
            niceTime += (date.getTime() > now.getTime()) ? "from now" : "ago"
        }

        return niceTime

    }


    def dateFromNow = { attrs ->

        def date = attrs.date

        out << getNiceDate(date)

    }

    def niceDate = { attrs ->

        def date = attrs.date
        def sdf = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm")
        out << sdf.format(date)

    }

    def blogDateRolodex = { attrs ->

        def date = attrs.date

        def sdf = new java.text.SimpleDateFormat("dd")
        def smf = new java.text.SimpleDateFormat("MMM")
        def syf = new java.text.SimpleDateFormat("yyyy")

        out << '<div class="dateblock">'
        out << '<div class="month"> '
        out << smf.format(date).toUpperCase()
        out << '</div><div class="day">'
        out << sdf.format(date).toUpperCase()
        out << '</div><div class="year">'
        out << syf.format(date).toUpperCase()
        out << "</div></div>"

    }

    def blogTitle = { attrs ->

        def blogId = attrs.blogid
        if (blogId) {
            Blog blog = Blog.findByBlogid(blogId)
            out << blog.title ? blog.title : ""
        } else {
            out << "Gravl"
        }

    }

    def blogByline = { attrs ->
        def blogId = attrs.blogid
        if (blogId) {
            Blog blog = Blog.findByBlogid(blogId)
            out << blog.byline ? blog.byline : "Subtitle here"
        } else {
            out << "Grails based blog engine"
        }
    }


    def feedburner = { attr ->

        if (ConfigurationHolder.config.http..usefeedburner) {
            out << """
			<p style='margin-top: 5px'>
					<img src="http://feeds.feedburner.com/~fc/groovyblogs?bg=99CCFF&amp;fg=444444&amp;anim=0" height="26" width="88" style="border:0" alt="Feedburner Stats" />
			</p>
			"""
        }

    }

}