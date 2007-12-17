class ImageController {

    def display = {

        log.debug "Displaying image ${request.uri} for blog ${params.blog}"
        // log.debug request.dump()
        String uri = request.uri.toString()
        String configDir = grailsApplication.config.blogdata.dir
        def fileName = configDir + params.blog + "/" +
                uri.substring(uri.indexOf("images"))
        log.debug "Rendering image from $fileName"
        File image = new File(fileName)
        if (image.exists() && image.canRead()) {
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1)
            log.debug "Setting response type to image/${ext}"
            response.setContentType("image/" + ext)
            byte[] b = image.readBytes();
            response.setContentLength(b.length)
            response.getOutputStream().write(b)
        } else {
            log.debug "Image not found or not readable"
            response.sendError(404);
        }

    }
}