import java.util.zip.ZipFile
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ImportService implements Serializable {

    boolean transactional = false

    static scope = "conversation"

    File zipFile
    String blogId = "Imported Blog"
    String blogType

    // stuff related to the import progress
    boolean stillImporting
    int totalEntries
    int progressCount
    int entriesImported
    int commentsImported


    def supportedTypes = ["Mock", "Pebble", "Gravl"]


    public int percentComplete() {
        return progressCount / totalEntries * 100
    }


    public void importBlog() {

        if (blogType ==~ /Pebble/) {
            importPebbleZip()
        } else if (blogType ==~ /Mock/) {
            importMockZip()
        } else {
            log.error("Unsupported blog type [$blogType]")
        }

    }

    private void resetCounters() {
        // reset our counters
        progressCount = 0
        entriesImported = 0
        commentsImported = 0
        stillImporting = true

    }

    public void importMockZip() {
        resetCounters()
        totalEntries = 100

        log.debug "Starting mock import"
        Thread.start {
            log.debug "Starting thread"
            while (stillImporting && progressCount < totalEntries) {
                progressCount++
                log.debug "Importing entry $progressCount of $totalEntries"
                Thread.sleep(200)
            }
            log.debug "Exiting thread"
        }
        log.debug "Exiting mock import"

    }

    public void importPebbleZip() {

        log.debug "Importing Pebble ZipFile ${zipFile.name} into ${blogId}"

        def blogZip = new ZipFile(zipFile)

        def entries = blogZip.entries()

        // reset our counters
        resetCounters()
        totalEntries = blogZip.size()

        // 28 Nov 2007 11:15:11:519 +0000
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss:SSS Z")


        Thread.start {
            Blog.withTransaction {status ->  // if not transactional your Hibernate session will disappear
                log.debug "Starting thread"

                Blog newBlog = new Blog(title: "Imported Pebble Blog", blogid: blogId).save()

                blogZip.entries().each {entry -> // a ZipEntry


                    if (!stillImporting)
                        return // exit thread safely

                    progressCount++


                    // trim entries in format 2007/11/28/1196248511519.xml
                    if (entry.name =~ /\d{4}\/\d{2}\/\d{2}\/\d{13}.xml$/) {
                        log.debug "Importing ${entry.name}"
                        String entryText = blogZip.getInputStream(entry).getText()
                        def blogEntry = new XmlSlurper().parseText(entryText)

                        def comments = blogEntry.comment
                        def categories = blogEntry.category
                        def tags = blogEntry.tags

                        log.debug "Title: $blogEntry.title (${comments.size()} comments, ${categories.size()} categories, ${tags.size()} tags)"


                        BlogEntry newEntry = new BlogEntry(title: blogEntry.title.toString(),
                                subtitle: blogEntry.subtitle.toString(),
                                excerpt: blogEntry.excerpt.toString(), body: blogEntry.body.toString(),
                                created: sdf.parse(blogEntry.date.toString()),
                                status: blogEntry.state.toString())
                        entriesImported++
                        newBlog.addToBlogEntries(newEntry).save()
                        newBlog.errors.allErrors.each {
                            log.debug it
                        }
                        newEntry.errors.allErrors.each {
                            log.debug it
                        }
                        log.debug "New Id of entry is ${newEntry.id}"

                        categories.each {category ->
                            String niceCat = category.toString()
                            niceCat = niceCat.replaceAll('/', '') // strip slashs
                            def tag = Tag.findByBlogAndName(newBlog, niceCat)
                            if (tag == null) {
                                tag = new Tag(name: niceCat)
                                log.debug "Creating new tag for ${niceCat}"
                                newBlog.addToTags(tag).save()
                            } else {
                                log.debug "Found existing tag for ${niceCat}"
                            }
                            newEntry.addToTags(tag).save()
                        }


                        comments.each {c ->
                            Comment newComment = new Comment(body: c.body.toString(), author: c.author.toString(),
                                    email: c.email.toString(), url: c.website.toString(),
                                    created: sdf.parse(c.date.toString()), status: c.state.toString())
                            newEntry.addToComments(newComment).save()
                            commentsImported++
                        }

                    } else if (entry.name =~ /.*images\/.*/) {

                        def fileName = ConfigurationHolder.config.data.dir +
                                entry.name.substring(f.indexOf("/images"))

                        log.debug "Processing Image file: $fileName"
                        def f = new File(fileName)
                        def p = new File(f.getParent())

                        // make any parent dirs..
                        if (!p.exists())
                            p.mkdirs()

                        def fos = new FileOutputStream(f)
                        blogZip.getInputStream(entry).eachByte { b ->
                            fos.write(b)
                        }

                        


                    } else if (entry.name =~ /blog.properties/) {

                        log.debug "Processing Blog Properties file"
                        def props = new Properties()
                        props.load(blogZip.getInputStream(entry))
                        // log.debug("Properties are: ${props.dump()}")
                        if (props.name) {
                            log.debug "Setting blog name to [$props.name]"
                            newBlog.title = props.name
                            newBlog.save()
                        }

                    } else {
                        log.debug("Skipping non-blog entry ${entry.name}")
                    }

                }
                log.debug "Exiting Pebble Import Thread"
            }
        }
        log.debug "Exiting Pebble Import"

    }

}