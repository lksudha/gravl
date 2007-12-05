class BootStrap {

     def init = { servletContext ->

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
        }

     }
     def destroy = {
     }
} 