package Screens.HomeScreen

import Screens.ProfileScreen.ProfileActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.habiceb_ares_finalproject.R
import database.UserSession
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvHomeUser = findViewById<TextView>(R.id.tvHomeUser)

        // Reactive data binding
        lifecycleScope.launch {
            UserSession.userState.collectLatest { user ->
                tvHomeUser.text = user.username
            }
        }

        findViewById<Button>(R.id.btnShopNow).setOnClickListener {
            Toast.makeText(this, "shop option implementing soon", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}