package Screens.LoginScreen

interface LoginContract {
    interface View {
        fun navigateToHome()
        fun navigateToRegister()
        fun showErrorMessage(msg: String)
    }
    interface Presenter {
        fun onLoginClicked(email: String, pass: String)
    }
}