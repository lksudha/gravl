class LoginControllerTests extends GroovyTestCase {

    /** Setup metaclass fixtures for mocking. */
    void setUp() {
        out = new StringWriter()
        LoginController.metaClass.getSession = { -> [ account: "user"] }
        LoginController.metaClass.getRequest = { -> [ method:"POST", xhr:false ] }
        LoginController.metaClass.getFlash =   { -> [:] }
        LoginController.metaClass.getParams =  { -> [:] }
    }

    /** Remove metaclass fixtures for mocking. */
    void tearDown() {
        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove LoginController
    }

    void testLogout() {

        LoginController lc = new LoginController()
        assertNotNull lc.session.account
        
        lc.logout()
        assertNull lc.session.account
        

    }

    void testForm() {
        
        def renderParams = [:]
        LoginController.metaClass.getRequest = {-> [method:"GET"] }
        LoginController.metaClass.render = { Map args -> renderParams = args }

        def controller = new LoginController()

        controller.form()

        assertEquals "form",renderParams.view
    }




}