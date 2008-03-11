class LoginController {

    def index = {redirect(action: 'form')}

    def login = {LoginCommand cmd ->

        log.debug "Attempting login"
        if (cmd.hasErrors()) {
            redirect(action: 'form')
        } else {

            Account account = Account.findByUserIdAndPassword(cmd.userId, cmd.password.encodeAsSha1())
            if (account) {
                session.account = account
             } else {
                session.account = null
                flash.loginError = "Invalid username or password. Please try again"
            }

            redirect(uri: "/${params.blog}/")

        }

    }

    def logout = {
        if (session.account) {
            session.account = null
        }

        redirect(uri: "/${params.blog}/")  // to the blog they logged out from
    }

    def form = {
        // placeholder for login page
    }
}