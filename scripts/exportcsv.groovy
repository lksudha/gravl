
println "Sparking"

def writer = new au.com.bytecode.opencsv.CSVWriter(new FileWriter("glen-blog-export.csv"))

List<String> firstLine = [ "csv_post_title","csv_post_post","csv_post_categories","csv_post_date","csv_post_slug" ]

1.upto(24) { i->
	firstLine.addAll( 
["csv_comment_${i}_author","csv_comment_${i}_content","csv_comment_${i}_author_email","csv_comment_${i}_url","csv_comment_${i}_date"]
	)
}
writer.writeNext(firstLine.toArray() as String[])

List<BlogEntry> be = BlogEntry.findAllWhere(status: "published")
be.each { entry ->
	List<String> line = []
	line << entry.title
	line << entry.body
	line << (entry.tags*.name).join(",")
	line << entry.created
	line << entry.title.encodeAsNiceTitle() // slug/permalink
	int ci = 1
	
	entry.comments.each { comment ->
		line << comment.author ?: ""
		line << comment.body ?: ""
		line << comment.email ?: ""
		line << comment.url ?: ""
		line << comment.created ?: ""
		ci++ 	
	}
	
	ci.upto(24) { // pad other comments
		[ "", "", "", "", ""].each { blanker -> 
			line << blanker
		}
	}
		
	writer.writeNext(line.toArray() as String[])
}
writer.flush()