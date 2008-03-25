import groovy.mock.interceptor.*

class NotificationServiceTests extends GroovyTestCase {

    NotificationService ns


    /** Setup metaclass fixtures for mocking. */
    void setUp() {

        def logger = new Expando( debug: { println it }, info: { println it },
                                  warn: { println it }, error: { println it } )
        NotificationService.metaClass.getLog = { -> logger }

        ns = new NotificationService()
        

    }

    /** Remove metaclass fixtures for mocking. */
    void tearDown() {

        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove NotificationService
        remove Comment
        remove String


    }


    void OFFtestIsEmailActive() {

        BlogProperty bp = new BlogProperty(name: 'emailNotify', value: true)
        Blog b = new Blog(blogProperties: [ bp ])
        BlogEntry be = new BlogEntry(blog: be)
        Comment c = new Comment(blogEntry: be)
        assertTrue ns.isEmailNotifyActive(c)



    }

    void testIsEmailActive() {

        def bp = [name: 'emailNotify', value: 'true']
        Comment.metaClass.getBlogEntry = { -> [ blog: [ blogProperties: [
                bp
        ] ] ] }
        def comment = new Comment()
        println  comment.blogEntry.blog.blogProperties
        assertTrue ns.isEmailNotifyActive(comment)
        bp.value = 'false'
        assertFalse ns.isEmailNotifyActive(comment)
        

        GroovySystem.metaClassRegistry.removeMetaClass Comment

        
    }

    void testSendEmailNotification() {


        String.metaClass.encodeAsWiki = { -> delegate }

        def bp = [name: 'emailNotify', value: 'true']
        Comment.metaClass.getBlogEntry = { -> [ blog: [ blogProperties: [
                bp
        ] ], toPermalink: {}, title: 'Sample Post' ] }
        def comment = new Comment(body: "sample body")
        def mockMail = new MockFor(MailService.class)
        mockMail.demand.send(1) { address, body, title -> "done" }
        
        mockMail.use {
            ns.mailService = new MailService()

            ns.sendEmailNotification(comment,"abc@abc.com", "http://localhost/demo/")

        }
        





    }



}