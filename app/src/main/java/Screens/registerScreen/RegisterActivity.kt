package Screens.registerScreen

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import Screens.LoginScreen.LoginActivity

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)

        val emailField = findViewById<EditText>(R.id.etRegEmail)
        val passwordField = findViewById<EditText>(R.id.etRegPassword)
        val confirmPasswordField = findViewById<EditText>(R.id.etConfirmPassword)
        val createBtn = findViewById<Button>(R.id.btnCreateAccount)
        val backToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        createBtn.setOnClickListener {
            presenter.onRegisterClicked(
                emailField.text.toString(),
                passwordField.text.toString(),
                confirmPasswordField.text.toString()
            )
        }

        backToLogin.setOnClickListener { finish() }
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
