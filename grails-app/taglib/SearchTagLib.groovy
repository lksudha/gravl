class SearchTagLib {
	
	def searchBox = { attrs ->
		out << "<div id='searchBox'>"
		out << "<form action='"
		
		// from ApplicationTagLib
		out << grailsAttributes.getApplicationUri(request)
		// if the current attribute null set the controller uri to the current controller
        if(attrs["controller"]) {
            out << '/' << attrs.remove("controller")
        }
        else {
           out << grailsAttributes.getControllerUri(request)
        }
        if(attrs["action"]) {
            out << '/' << attrs.remove("action")
        }
		
		out << "' method='post'>"
		
		if (attrs.query == null) { attrs.query = "" }
		
		out << "<input id='searchField' size='20' name='query' value='${attrs.query}'/> "
		
		
		int selectedMaxHits
		if (params.hitcount) {
			selectedMaxHits = Integer.parseInt(params.hitcount)
		} else if (attrs.hitcount) {
			selectedMaxHits = Integer.parseInt(attrs.hitcount)
		} else {
			selectedMaxHits = 10
		}
		
		if (!attrs.noCombo) {
			out << "<select name='hitcount'>"
			for (i in [10,20,30,40,50]) {
				out << "<option value='$i' " + (i==selectedMaxHits ? "selected='selected' " : "") + ">$i hits</option>"
			}
			out << "</select> "
		}
		
		out << "<input type='hidden' name='fields' value='${attrs.fields}'/>"
		out << "<input id='searchButton' type='submit' value='Search'/>"
		
		out << "</form>"
		out << "</div>"
	
	}
	
	def searchResults = { attrs ->
	
		def searchResults = attrs.results
		def titleField = attrs.titleField ? attrs.titleField : "title"
		def bodyField = attrs.bodyField ? attrs.bodyField : "body"
				
		
		for (result in searchResults.resultList) {

			out << "<div class='hit'>"
			// build a URI to the original document, we always store class and 
			// object id with the index for just this reason
//			def hitUrl = request.contextPath + "/" + result.document.get("class").toLowerCase() + 
//				+ "/show/" + result.document.get("id")

            long blogDate = Long.parseLong(result.document.get("created"))

            println "Result: " + result.dump()

            def hitUrl = request.contextPath + result.document.get("permalink")

			out << "<div class='hitEntry'>"
            out << "<div class='hitTitle'>"
			out << "<a href='${hitUrl}'>"
			out << result.highlight[titleField]
			out << "</a>"
			out << "</div>"
			out << "<div class='hitInfo'>"
			out << new Date(blogDate)
			out << "</div>"
			out << "<p class='hitBody'>" + result.highlight[bodyField] + "</p>"
			out << "</div>"
			out << "</div>"
		}
		
	
	}
	

}