/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Dec 3, 2007
 * Time: 8:06:13 PM
 * To change this template use File | Settings | File Templates.
 */

class ImportServiceTests extends GroovyTestCase {

    public void testImport() {
        println "Starting test.."
        def file = "/Users/glen/Desktop/glen-20071203.zip"
        ImportService is = new ImportService()
        is.importPebbleZip(file)
    }
}