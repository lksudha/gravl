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
        println "\n>>> Setting dir to [${dir}]\n"

        [ files: files, dirName: dir ]

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
        redirect(uri: "/${params.blog}/files/list?dir=${dir}")

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
        redirect(uri: "/${params.blog}/files/list?dir=${dir}")

    }

    def rename = {

        def newName = params.value
        def filename = params.filename
        def dir = params.dir
        def blogId = params.blog

        String configDir = grailsApplication.config.blogdata.dir
        String from = "${configDir}/${blogId}/${dir}/${filename}"
        String to = "${configDir}/${blogId}/${dir}/${newName}"
        log.debug "Renaming file at ${from} to ${newName}"

        File f = new File(from)
        if (f.exists()) {
            f.renameTo(new File(to))
            render "${newName}"
        } else {
            // all bad, but how to break the news? 
            render "${filename}"
        }

    }



}