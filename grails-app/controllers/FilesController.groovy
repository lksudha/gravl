/**
 * @author Glen Smith
 */
class FilesController {
    
    def defaultAction = "list"

    def list = {

        def blogId = params.blog
        def dir = params.dir ?: ""

        dir = dir.replaceAll("\\.\\./", "") // strip any dodgy navigation

        String configDir = grailsApplication.config.blogdata.dir
        String target = configDir + blogId + dir
        log.debug "Looking in [$target]"
        def files = [ ]
        File d  = new File(target)
        if (d.exists() && d.isDirectory()) {
            files = d.listFiles()
        }

        return [ files: files, dir: dir ]

    }

    def upload = {
        def f = request.getFile('myFile')
        def filename = params.filename
        def dir = params.dir
        def blogId = params.blog

        String configDir = grailsApplication.config.blogdata.dir
        String target = "${configDir}/${blogId}/${dir}/${filename}"
        log.debug "Writing new file to ${target}"

        if (!f.empty) {
            f.transferTo(new File(target))
            flash.message = "Successfully uploaded file: ${filename}"
        }
        else {
            flash.message = 'file cannot be empty'
        }
        redirect(controller: "${params.blog}/files", action: 'list', params: [dir: dir])

    }

    def delete = {
        def filename = params.filename
        def dir = params.dir
        def blogId = params.blog

        String configDir = grailsApplication.config.blogdata.dir
        String target = "${configDir}/${blogId}/${dir}/${filename}"
        log.debug "Deleting file at ${target}"

        File f = new File(target)
        if (f.exists()) {
            f.delete()
            flash.message = "Successfully deleted file: ${filename}"
        } else {
            flash.message = "File does not exist or is readonly: ${filename}"
        }
        redirect(controller: "${params.blog}/files", action: 'list', params: [dir: dir])

    }



}