class BootStrap {

    def init = {servletContext ->

        if (Account.count() == 0) {
            println "No accounts detected... Creating Admin user... "
            String passwordDigest = 'admin'.encodeAsSha1()
            def acc = new Account(userId: 'admin', role: 'admin', password: passwordDigest,
                    email: 'youremail@yourdomain.com', fullName: 'Admin User')
            if (!acc.validate()) {
                println "Admin account does not validate! Errors:"
                acc.errors.allErrors.each {
                    println it
                }
            }

            println acc.save()
            Blog blog = new Blog(title: 'Gravl Sample Blog', byline: 'We Are Up and Running...', blogid: 'sample',
                    allowComments: true)
            blog.save()

            Tag tag = new Tag(name: 'sample', blog: blog)
            tag.save()

            BlogEntry be = new BlogEntry(title: "Welcome to Gravl", status: "published",
                    body: """
<p>
Your blog is up and running. You can login through the sidebar
using the username <b>admin</b> with password <b>admin</b>. Once you are
up and running, you can change the name of this blog using
the <b>properties</b> link.
</p>
""")
            be.addToTags(tag).save()
            blog.addToBlogEntries(be).save()

        }

    }
    def destroy = {
    }
} 