// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = false // enables the parsing of file extensions from URLs into the request format

grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text-plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// The default codec used to encode data with ${}
grails.views.'default.codec'="none" // none, html, base64

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// local gravl config data
// from controllers:   def propValue = grailsApplication.config.my.property
// from services: ConfigurationHolder.config.my.custom.data
// import org.codehaus.groovy.grails.commons.ConfigurationHolder
pdf {
	dir=System.properties["java.io.tmpdir"]
}

blogdata {
    dir="/data/gravl/"
    index="/data/gravl/index/"
}

cache {
	enabled = true
}


mail {
	host ="smtp.gmail.com"
	port = 465
	username = "your.username@gmail.com"
	password = "yourpassword"
	from="glen@bytecode.com.au"
	subject="Welcome to gravl"
    enabled=true
    ssl=true
    props = ["mail.smtp.auth":"true", 					   
              "mail.smtp.socketFactory.port":"465",
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]
}


chat {
	serviceName = "gmail.com"
    host = "talk.google.com"
    port = 5222
    username = "your.username@gmail.com"
    password = "password"
}

http {
	useproxy=true
	host="192.168.1.7"
	port=3128
	useragent="Gravl/1.0 (http://code.google.com/gravl)"
	usefeedburner=false

	feedburner_atom="http://feeds.feedburner.com/gravl"
	feedburner_rss="http://feeds.feedburner.com/gravl"

}

// you can configure bean attributes from here tooo
beans {
       cacheService {
           nameToTimeout {
               //referrers=60
           }
       }
}




// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails."web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails."web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate'

    warn   'org.mortbay.log'
    debug   "grails.app"

}




// The following properties have been added by the Upgrade process...

grails.views.default.codec="none" // none, html, base64

grails.views.gsp.encoding="UTF-8"

