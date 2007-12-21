class TableTagLib {

    // turn a map into a table
    // takes a map="[ : ]" and an optional headings="[one,two,three]"
    def tableFromMap = { attrs ->

		def map = attrs.map
		def headings = attrs.headings

		out << '<table>'
		if (headings) {
            out << '<tr>'
            headings.each { heading ->
                out << "<th>$heading</th>"
            }
            out << '</tr>'
        }
        if (map) {
            int counter = 0
            map.each { key, value ->
                def style = (counter % 2) ? "even" : "odd"
                out << "<tr style='$style'><td>$key</td><td>$value</td></tr>"
            }
        }

		out << '</table>'

	}

}