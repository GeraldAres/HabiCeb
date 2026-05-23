package Screens.LoginScreen
import Screens.HomeScreen.HomeActivity
import Screens.registerScreen.RegisterActivity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import database.UserSession

//import com.example.habiceb.R

class LoginActivity : AppCompatActivity(), LoginContract.View{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val createNow = findViewById<TextView>(R.id.tvCreateNow)

        loginBtn.setOnClickListener {
            val email = emailField.text.toString()
            val pass = passwordField.text.toString()

            // Basic authentication logic using UserSession
            if (email == UserSession.email && pass == UserSession.password) {
                Toast.makeText(this, "welcome to habi ceb", Toast.LENGTH_SHORT).show()
                navigateToHome()
                finish() // Close login screen so user can't go back to it
            } else {
                showErrorMessage("Invalid Credentials")
            }
        }

        createNow.setOnClickListener { navigateToRegister() }
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showErrorMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}