class Tag
{
    static hasMany = [entries: BlogEntry]
    static belongsTo = [BlogEntry,Blog]
    // static constraints = {}

    String name
    Blog blog

    String toString()
    {
        return "Tag ${id} - ${name}"
    }
}
