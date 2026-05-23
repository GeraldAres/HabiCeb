package Screens.LoginScreen

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import Screens.HomeScreen.HomeActivity
import Screens.registerScreen.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)

        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val createNow = findViewById<TextView>(R.id.tvCreateNow)

        loginBtn.setOnClickListener {
            val email = emailField.text.toString()
            val pass = passwordField.text.toString()
            presenter.onLoginClicked(email, pass)
        }

        createNow.setOnClickListener { navigateToRegister() }
    }

    override fun navigateToHome() {
        Toast.makeText(this, "welcome to habi ceb", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showErrorMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
