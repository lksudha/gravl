class NotificationServiceTests extends GroovyTestCase {

    NotificationService ns


    /** Setup metaclass fixtures for mocking. */
    void setUp() {


        def mailService = new Expando()
        NotificationService.metaClass.mailService = { -> mailService }
        ns = new NotificationService()
        

    }

    /** Remove metaclass fixtures for mocking. */
    void tearDown() {

        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove NotificationService


    }


    void OFFtestIsEmailActive() {

        BlogProperty bp = new BlogProperty(name: 'emailNotify', value: true)
        Blog b = new Blog(blogProperties: [ bp ])
        BlogEntry be = new BlogEntry(blog: be)
        Comment c = new Comment(blogEntry: be)
        assertTrue ns.isEmailNotifyActive(c)



    }

    void testIsEmailActive() {

        Comment.metaClass.getBlogEntry = { -> [ blog: [ blogProperties: [
                [ name: 'emailNotify', value: false ],
                [ name: 'gtalkNotify', value: 'false' ]
        ] ] ] }
        def comment = new Comment() 
        println  comment.blogEntry.blog.blogProperties
        def result = ns.isEmailNotifyActive(comment)
        println "Result is $result"
        if (result) {
            println true
        } else {
            println false
        }
        //assertTrue ns.isEmailNotifyActive(comment)

        GroovySystem.metaClassRegistry.removeMetaClass Comment

        
    }



}