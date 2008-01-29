class TableTagLib {






    private String getMarkupName(url) {

        def urlStartsWithMap = [
            "http://www.google" : "Google",
            "http://www.groovyblogs.org" : "GroovyBlogs",
            "http://groovyblogs.org" : "GroovyBlogs",
            "http://search.live.com/" : "MS Live Search",
            "http://www.javablogs.com/" : "Javablogs"
        ]

        def markupName = url

        urlStartsWithMap.each { key, value ->
            if (url.startsWith(key)) {
                markupName = value

                def matcher = url =~ /\Wq=([^&]+)/
                if ( matcher ) {
                    markupName += ": " + matcher.group(1).decodeURL()
                }

            }

        }
        
        if (markupName && markupName.size() > 90) {
        	// draw the line at 90 chars...
        	markupName = markupName[0..90] + "..."
        }

        return markupName

    }


    // turn a map into a table
    // takes a map="[ : ]" and an optional headings="[Country,Population]"
    def tableFromMap = { attrs ->

		def map = attrs.map
		def headings = attrs.headings
		def url = attrs.url ? true : false

		out << '<table class="prettyTable">'
		if (headings) {
            out << '<thead><tr>'
            headings.each { heading ->
                out << "<th>$heading</th>"
            }
            out << '</tr></thead>'
        }
        if (map) {
            int counter = 0
            int total = 0
            out << "<tbody>"
            map.each { key, value ->
                def style = (counter++ % 2) ? "even" : "odd"
                out << "<tr class='${style}'><td class='leftcol'>"
                if (url) {
                    out << "<a href='${key}'>" << getMarkupName(key) << "</a>"
                } else {
                    out << key
                }
                out << "</td><td class='rightcol'>$value</td></tr>"
                total += value
            }
            out << "<tr class='total'><td><i>Total</i></td><td><i>${total}</i></td></tr>"
            out << "</tbody>"
        }

		out << '</table>'

	}

}