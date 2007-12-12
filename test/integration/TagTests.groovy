class TagTests extends GroovyTestCase {

    // simple test case for the tagcloud
    public void testTagCloud() {

        Blog blog = new Blog(title: 'JUnit Blog', blogid: "junit").save()
        println blog.errors.dump()

        // setup some mock tags...
        def tags = []
        1.upto(5) {i ->
            tags << new Tag(name: "tag-${i}", blog: blog).save()
        }

        //  now create some entries, each with some tags
        def entries = []
        0.upto(100) {i ->
            BlogEntry be = new BlogEntry(title: "entry-${i}", body: "body-${i}")
            blog.addToBlogEntries(be).save()
            tags[0..i % 5].each {tag ->
                be.addToTags(tag).save()
            }
            entries << be
        }
        blog.save(flush: true)

        // now test our tagcloud
        new TagCloudTagLib().tagCloud(blogId: "junit");

    }
}
