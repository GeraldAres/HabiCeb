package Screens.registerScreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import Screens.LoginScreen.LoginActivity
import database.UserSession

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailField = findViewById<EditText>(R.id.etRegEmail)
        val passwordField = findViewById<EditText>(R.id.etRegPassword)
        val confirmPasswordField = findViewById<EditText>(R.id.etConfirmPassword)
        val createBtn = findViewById<Button>(R.id.btnCreateAccount)
        val backToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        createBtn.setOnClickListener {
            val email = emailField.text.toString()
            val pass = passwordField.text.toString()
            val confirmPass = confirmPasswordField.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (pass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Save user to session
                UserSession.email = email
                UserSession.password = pass
                // Set username as the part before @ in email
                UserSession.username = email.substringBefore("@")

                // As per PDF instructions: Toast then redirect
                Toast.makeText(this, "registered successfully", Toast.LENGTH_SHORT).show()

                // Redirect to Login Screen
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Close register screen
            }
        }

        // Back to login link
        backToLogin.setOnClickListener {
            finish() // Simply goes back to the previous screen (Login)
        }
    }
}