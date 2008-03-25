import groovy.mock.interceptor.*

class CountryLookupServiceTests2 extends GroovyTestCase {

    CountryLookupService cls


    /** Setup metaclass fixtures for mocking.  */
    void setUp() {

        def logger = new Expando(debug: {println it}, info: {println it},
                warn: {println it}, error: {println it})
        CountryLookupService.metaClass.getLog = {-> logger}

        cls = new CountryLookupService()

    }

    /** Remove metaclass fixtures for mocking.  */
    void tearDown() {

        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove CountryLookupService

    }





    void testSendEmailNotification() {

        def mockLookupService = new MockFor(com.maxmind.geoip.LookupService.class)
        mockLookupService.demand.getCountry(1) {ipaddress -> [name: "Australia"]}

        mockLookupService.use {
            println cls.getCountryName("192.168.1.1")

        }

    }


}