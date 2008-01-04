class BlogPropertyController {
    def scaffold = BlogProperty

    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max)params.max = 10
        [ blogPropertyList: BlogProperty.list( params ) ]
    }

    def show = {
        [ blogProperty : BlogProperty.get( params.id ) ]
    }

    def delete = {
        def blogProperty = BlogProperty.get( params.id )
        if(blogProperty) {
            blogProperty.delete()
            flash.message = "BlogProperty ${params.id} deleted."
            redirect(action:list)
        }
        else {
            flash.message = "BlogProperty not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def blogProperty = BlogProperty.get( params.id )

        if(!blogProperty) {
                flash.message = "BlogProperty not found with id ${params.id}"
                redirect(action:list)
        }
        else {
            return [ blogProperty : blogProperty ]
        }
    }

    def update = {
        def blogProperty = BlogProperty.get( params.id )
        if(blogProperty) {
             blogProperty.properties = params
            if(blogProperty.save()) {
                flash.message = "BlogProperty ${params.id} updated."
                redirect(action:show,id:blogProperty.id)
            }
            else {
                render(view:'edit',model:[blogProperty:blogProperty])
            }
        }
        else {
            flash.message = "BlogProperty not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
      def blogProperty = new BlogProperty()
      blogProperty.properties = params
      return ['blogProperty':blogProperty]
    }

    def save = {
        def blogProperty = new BlogProperty()
        blogProperty.properties = params
        if(blogProperty.save()) {
            flash.message = "BlogProperty ${blogProperty.id} created."
            redirect(action:show,id:blogProperty.id)
        }
        else {
            render(view:'create',model:[blogProperty:blogProperty])
        }
    }

}