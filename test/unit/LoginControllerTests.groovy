class LoginControllerTests extends GroovyTestCase {

    /** Setup metaclass fixtures for mocking. */
    void setUp() {

        def logger = new Expando( debug: { println it }, info: { println it },
                                  warn: { println it }, error: { println it } )
        LoginController.metaClass.getLog = { -> logger }

    }

    /** Remove metaclass fixtures for mocking. */
    void tearDown() {
        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove LoginController
        remove LoginCommand
        remove Account
        remove String

    }


    void testIndexRedirect() {

        def redirectParams = [ : ]
        LoginController.metaClass.redirect = { Map args -> redirectParams = args  }

        LoginController lc = new LoginController()
        lc.index()

        assertEquals "form" , redirectParams.action


    }



    void testLogin() {


        String.metaClass.encodeAsSha1 = { ->  delegate }

        def session = [ : ]
        LoginController.metaClass.getSession = { -> session }

        def params = [ blog : 'demo' ]
        LoginController.metaClass.getParams = { -> params }

        def redirectParams = [ : ]
        LoginController.metaClass.redirect = { Map args -> redirectParams = args  }

        def flash = [ : ]
        LoginController.metaClass.getFlash = { -> flash }

        Account.metaClass.static.findByUserIdAndPassword = { String userId, String password ->
           if (userId == 'glen' && password == 'sesame' ) {
                return [ userId: 'glen' ]
           } else {
                return null
           }

        }

        LoginCommand.metaClass.hasErrors = { false }

        LoginCommand goodUser = new LoginCommand(userId: 'glen', password: 'sesame')

        LoginController lc = new LoginController()
        lc.login(goodUser)

        assertEquals "glen", session.account.userId
        assertEquals "/demo/", redirectParams.uri
        assertNull flash.loginError

        LoginCommand badUser = new LoginCommand(userId: 'glen', password: 'unlucky')
        lc.login(badUser)

        assertNull session.account
        assertNotNull flash.loginError




    }



    void testLogout() {

        def session = [ account: "user"]
        LoginController.metaClass.getSession = { -> session }
        LoginController.metaClass.getParams = { -> [ blog : 'demo' ] }
        def redirectParams = [ : ]
        LoginController.metaClass.redirect = { Map args -> redirectParams = args  }

        LoginController lc = new LoginController()
        assertNotNull lc.session.account
        
        lc.logout()

        assertEquals "/demo/", redirectParams.uri

        assertNull lc.session.account
        

    }

    void testForm() {
        
        def renderParams = [:]
        
        LoginController.metaClass.render = { Map args -> renderParams = args }

        def controller = new LoginController()

        controller.form()

        // not sure how to test this yet...
        // assertEquals "", renderParams.view
    }




}