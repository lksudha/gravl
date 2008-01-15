class TableTagLib {

    // turn a map into a table
    // takes a map="[ : ]" and an optional headings="[one,two,three]"
    def tableFromMap = { attrs ->

		def map = attrs.map
		def headings = attrs.headings

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
            out << "<tbody>"
            map.each { key, value ->
                def style = (counter % 2) ? "even" : "odd"
                out << "<tr style='$style'><td class='leftcol'>$key</td><td class='rightcol'>$value</td></tr>"
            }
            out << "</tbody>"
        }

		out << '</table>'

	}

}