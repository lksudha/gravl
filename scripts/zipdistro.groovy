/*
   Small Gant script to zip up a distro of Gravl.

   You can run "grails zipdistro" to generate the zip.
*/

target(default: "Zips an application distro for upload to googlecode etc") {

    def baseDir = Ant.project.properties.basedir

    Ant.property ( file : 'application.properties' )
    def appName = Ant.project.properties.'app.name'
    def appVersion = Ant.project.properties.'app.version'

    println "Starting Zip Disto creation for ${appName} version ${appVersion} at ${new Date()}"
    def zipDir = "distro"
    def zipName = "${appName}-${appVersion}"
    def buildDir = "${zipDir}/${zipName}"
    def tarName = "${buildDir}.tar"
    def tgzName = "${tarName}.gz"



    // println "${Ant.project.properties}"

    Ant.delete(dir: buildDir)
    Ant.mkdir(dir: buildDir)

    copy ( todir : buildDir ) {
        fileset ( dir : baseDir ) {
            include ( name: "src/**/*.*")
            include ( name: "test/**/*.*")
            include ( name: "lib/**/*.*")
            include ( name: "spring/**/*.*")
            include ( name: "hibernate/**/*.*")
            include ( name: "scripts/**/*.*")
            include ( name: "plugins/**/*.*")
            include ( name: "grails-app/**/*.*")
            include ( name: "web-app/**/*.*")
            exclude ( name: "**/WEB-INF/lib/**/*.*")
        }
        fileset ( dir : baseDir, includes: "*.*", excludes: "*.war,*.zip,*.log")
    }

   tar( destfile: tarName, basedir: zipDir, includes: "${zipName}/**/*.*" )
   gzip( src: tarName, destfile: tgzName )
   delete ( file: tarName )
   Ant.delete( dir: buildDir )


}


/*

<property name="dist.src.dir" location="distro"/>
		<property name="zip.dir.name" value="groovyblogs-1.0"/>

	       <delete dir="${dist.src.dir}/${zip.dir.name}" />
	        <mkdir dir="${dist.src.dir}/${zip.dir.name}" />

	        <copy todir="${dist.src.dir}/${zip.dir.name}">
	            <fileset dir="${basedir}">
	                <include name="src/** /*.*" />
	                <include name="test/** /*.*" />
	            	<include name="lib/** /*.*" />
	                <include name="art/** /*.*" />
	                <include name="spring/** /*.*" />
	                <include name="plugins/** /*.*" />
	                <include name="hibernate/** /*.*" />
	                <include name="scripts/** /*.*" />
	                <include name="grails-app/** /*.*" />
	                <include name="plugsins/** /*.*" />
	            	<include name="grails-test/** /*.*" />
	            	<include name="web-app/** /*.*" />
	            	<exclude name="** /WEB-INF/lib/*.*"/>
	            	<exclude name="** /testdb.*"/>
	            </fileset>
	            <fileset dir="${basedir}" includes="*.*" excludes="*.war,*.zip"/>
	        </copy>

	        <property name="src.tar.name" value="${dist.src.dir}/${zip.dir.name}.tar"/>

	        <tar destfile="${src.tar.name}"
	                   basedir="${dist.src.dir}" includes="${zip.dir.name}/** /*.*"
	              />

	        <gzip src="${src.tar.name}" destfile="${src.tar.name}.gz"/>

	        <delete file="${src.tar.name}"/>


*/
