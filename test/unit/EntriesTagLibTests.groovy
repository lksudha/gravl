import groovy.mock.interceptor.StubFor

// import groovy.mock.interceptor.StubFor

class EntriesTagLibTests extends GroovyTestCase {

    void testNiceDate() {

        // create taglib with mocked "out"
        StringWriter out = new StringWriter()
        EntriesTagLib.metaClass.out = out
        EntriesTagLib btl = new EntriesTagLib()


        Date now = new Date()

        btl.niceDate(date: now)

        assertEquals(
            new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(now),
            out.toString()
        )

    }

    void testDateFromNow() {

        // create taglib with mocked "out"
        StringWriter out = new StringWriter()


        EntriesTagLib.metaClass.out = out
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
