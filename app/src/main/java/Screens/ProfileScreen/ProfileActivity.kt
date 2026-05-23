package Screens.ProfileScreen

import Screens.SplashScreen.SplashActivity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import database.UserSession

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var editSection: LinearLayout
    private lateinit var etNewUsername: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvUsername = findViewById(R.id.tvProfileUser)
        editSection = findViewById(R.id.editProfileSection)
        etNewUsername = findViewById(R.id.etNewUsername)

        tvUsername.text = "@${UserSession.username}"

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            editSection.visibility = android.view.View.VISIBLE
        }

        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            val newName = etNewUsername.text.toString()
            if (newName.isNotEmpty()) {
                UserSession.updateUsername(newName)
                tvUsername.text = "@$newName"
                editSection.visibility = android.view.View.GONE
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.navHome).setOnClickListener { finish() }
    }
}