class BlogControllerTests extends GroovyTestCase {

    // mocked "out" for taglib
    StringWriter out

    /** Setup metaclass fixtures for mocking. */
    void setUp() {
        out = new StringWriter()
        EntriesTagLib.metaClass.out = out
    }

    /** Remove metaclass fixtures for mocking. */
    void tearDown() {
        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove EntriesTagLib
    }

}