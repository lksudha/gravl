import com.maxmind.geoip.LookupService

/**
  * Country lookups courtesy of GeoIp lite: http://www.maxmind.com/app/geolitecountry
  */
class CountryLookupService {
	
    boolean transactional = false

    LookupService lookupService

    private LookupService getLookupService() {
        if (lookupService == null) {
            // assume you put your data file in /src/java/resources
            def url = this.class.getResource("/resources/geoIp/GeoIP.dat")
            log.debug ("Looking for GeoIP database at: $url")
            lookupService = new LookupService(new File(url.toURI()),LookupService.GEOIP_MEMORY_CACHE);
        }
        return lookupService
    }

    def getCountryName(String ipAddress) {
        log.debug "Looking up address for ${ipAddress}"
        return getLookupService().getCountry(ipAddress).getName()
    }
}