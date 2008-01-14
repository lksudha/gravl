class ImageController {

    def mimeType = [ "mov" : "video/quicktime" ]
    def exts = [ "png", "jpg", "gif", "mov" ]

    def display = {

        println "Dumping request: ${request.dump()}"
        println "Displaying image ${request.uri} for blog ${params.blog}"
        // log.debug request.dump()
        String uri = request.requestURI.toString()
        String configDir = grailsApplication.config.blogdata.dir
        def fileName = configDir + params.blog + "/images/" + (params.dir ? params.dir + "/" : "") +
            params.file 

        println "Rendering image from $fileName"
        boolean found = false
        exts.each { ext ->
            File image = new File(fileName + "." + ext)
            println "Trying... ${image.absoluteFile}"
            if (image.exists() && image.canRead()) {
                println "Found image: ${image.absoluteFile}"
                // String ext = fileName.substring(fileName.lastIndexOf(".") + 1)
                if (mimeType[ext]) {
                    log.debug "Setting response type to ${mimeType[ext]}"
                    response.setContentType("image/${mimeType[ext]}")
                } else {
                    log.debug "Setting response type to image/${ext}"
                    response.setContentType("image/" + ext)
                }
                byte[] b = image.readBytes();
                response.setContentLength(b.length)
                response.getOutputStream().write(b)
                found = true
                return
            }
        }
        
        if (!found) {
            println "Image not found or not readable"
            response.sendError(404);
        }

    }
}