package Screens.registerScreen

interface RegisterContract {
    interface View {
        fun navigateToLogin()
        fun showMessage(msg: String)
        fun showError(msg: String)
    }
    interface Presenter {
        fun onRegisterClicked(email: String, pass: String, confirmPass: String)
    }
}
