class LoginController {

    def index = {redirect(action: 'form')}

    def login = {LoginCommand cmd ->

        if (cmd.hasErrors()) {
            redirect(action: 'form')
        } else {

            Account account = Account.findByUserIdAndPassword(cmd.userId, cmd.password.encodeAsSha1())
            if (account) {
                session.account = account
                redirect(controller: 'blog')
            } else {
                flash = "Invalid username or password. Please try again"
                redirect(action: 'show')
            }
        }

    }

    def form = {
        // placeholder for login page
    }
}