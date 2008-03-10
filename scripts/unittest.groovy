/*
   Small Gant script to zip up a distro of Gravl.

   You can run "grails zipdistro" to generate the zip.
*/

target(default: "Runs the unit tests for the application") {

    def baseDir = Ant.project.properties.basedir

    Ant.property ( file : 'application.properties' )
    def appName = Ant.project.properties.'app.name'
    def appVersion = Ant.project.properties.'app.version'

    def testsDirectory = 'test/unit'

    println "Running Units Tests for ${appName} version ${appVersion} at ${new Date()}"

    new File(testsDirectory).eachFileRecurse { file ->

        // println "${file.name}"
        if (!file.isDirectory() && file.name.endsWith("Tests.groovy")) {
            println "Testing ${file.name}"
        }

    }

/*
    Ant.junit ( printsummary : 'yes' , fork : 'true' ) {
        formatter ( type : 'plain' )
        batchtest ( todir : "/tmp" ) { fileset ( dir : testsDirectory , includes : '** /*Test.groovy' ) }

  }
*/
    

}