package Screens.HomeScreen

import Screens.ProfileScreen.ProfileActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import database.UserSession

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvHomeUser = findViewById<TextView>(R.id.tvHomeUser)
        tvHomeUser.text = UserSession.username

        findViewById<Button>(R.id.btnShopNow).setOnClickListener {
            Toast.makeText(this, "shop option implementing soon", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}