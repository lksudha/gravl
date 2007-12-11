class Tag
{
    static belongsTo = [Blog, BlogEntry]
    static hasMany = [ entries: BlogEntry ]
    // static constraints = {}

    String name
    Blog blog

    String toString()
    {
        return "Tag ${id} - ${name}"
    }
}
