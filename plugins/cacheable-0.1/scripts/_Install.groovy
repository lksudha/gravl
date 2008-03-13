
//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'Ant' to access a global instance of AntBuilder
//
Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

// Install the config file into the application directory.
if (!new File("${basedir}/grails-app/conf/CacheableConfiguration.groovy").exists()) {
	Ant.move(file:"${pluginBasedir}/grails-app/conf/CacheableConfiguration.groovy", 
	         todir:"${basedir}/grails-app/conf")
}
else if (new File("${pluginBasedir}/grails-app/conf/CacheableConfiguration.groovy").exists()) {
	Ant.delete(file:"${pluginBasedir}/grails-app/conf/CacheableConfiguration.groovy")
}