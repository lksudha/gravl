class CountryLookupServiceTests extends GroovyTestCase {

    void testCountryLookup() {

        def lookup = new CountryLookupService()
        assertEquals("Australia", lookup.getCountryName("150.101.115.141"))


    }
}