import groovy.mock.interceptor.*
import com.maxmind.geoip.LookupService

//class MockLookupService extends LookupService {
//    public MockLookupService() {
//
//    }
//
//    def country = { ipaddress ->
//       return [name: "Australia"]
//
//    }
//}

class CountryLookupServiceTests2 extends GroovyTestCase {

    CountryLookupService cls


    /** Setup metaclass fixtures for mocking.     */
    void setUp() {

        ExpandoMetaClass.enableGlobally()

        def logger = new Expando(debug: {println it}, info: {println it},
                warn: {println it}, error: {println it})
        CountryLookupService.metaClass.getLog = {-> logger}
        CountryLookupService.metaClass.'static'.getResource = {path -> println "Get Resource on ${path}"; new URL("file://////${path}")}

        LookupService.metaClass.constructor << {file, options ->
            null
        }

        cls = new CountryLookupService()

    }

    /** Remove metaclass fixtures for mocking.     */
    void tearDown() {

        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove CountryLookupService
        remove LookupService

    }





    void testCountryLookup() {

        println cls.getCountryName("192.168.1.1")

    }

}