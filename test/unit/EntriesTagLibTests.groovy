
class EntriesTagLibTests extends GroovyTestCase {

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

    void testNiceDate() {

        EntriesTagLib btl = new EntriesTagLib()

        Date now = new Date()

        btl.niceDate(date: now)

        assertEquals(
                new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(now),
                out.toString()
                )

    }

    void testDateFromNow() {

        EntriesTagLib btl = new EntriesTagLib()

        Calendar cal = Calendar.getInstance()

        btl.dateFromNow(date: cal.time)

        assertEquals "Right now", out.toString()

        // reset "out" buffer
        out.getBuffer().setLength(0)

        cal.add(Calendar.HOUR, -1)
        btl.dateFromNow(date: cal.time)

        assertEquals "1 hour ago", out.toString()

    }

}
