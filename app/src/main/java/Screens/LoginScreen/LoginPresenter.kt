package Screens.LoginScreen

import database.UserSession

class LoginPresenter(private val view: LoginContract.View) : LoginContract.Presenter {

    override fun onLoginClicked(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            view.showErrorMessage("Fields cannot be empty")
            return
        }

        if (email == UserSession.email && pass == UserSession.password) {
            view.navigateToHome()
        } else {
            view.showErrorMessage("Invalid Credentials")
        }
    }
}
