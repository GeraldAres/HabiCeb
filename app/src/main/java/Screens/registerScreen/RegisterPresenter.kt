package Screens.registerScreen

import database.UserSession

class RegisterPresenter(private val view: RegisterContract.View) : RegisterContract.Presenter {

    override fun onRegisterClicked(email: String, pass: String, confirmPass: String) {
        if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            view.showError("Please fill all fields")
            return
        }

        if (pass != confirmPass) {
            view.showError("Passwords do not match")
            return
        }

        // Save to Session
        UserSession.email = email
        UserSession.password = pass
        UserSession.username = email.substringBefore("@")

        view.showMessage("registered successfully")
        view.navigateToLogin()
    }
}
