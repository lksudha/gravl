class BlogEntryCommand {

    static constraints = {
        title(nullable: false, maxSize: 128)
        body(nullable: false, maxSize: 10000)
        subtitle(nullable: true, blank: true, maxSize: 128)
        excerpt(nullable: true, blank: true, maxSize: 1024)
        markup(inList: ['html', 'wiki'])
        status(inList: ['published', 'unpublished'])
    }

    int id  // id of already saved BlogEntry or 0 if fresh
    Date created = new Date()
    String title
    String subtitle
    String excerpt
    String body
    String markup = "html"
    String tagList // comma-sep list...

    String status = "unpublished"
    
    boolean allowComments = true

    String toString()
    {
        return "BlogEntry ${id} - ${title}"
    }

    def getAllTags() {
        return tagList.split(",")
    }

    public String toMarkup() {

        if (markup == "wiki") {
            return body.encodeAsWiki()
        } else {
            return body
        }

    }

}