package Screens.SplashScreen
import Screens.LoginScreen.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R

//import com.example.habiceb.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Click anywhere to redirect to login
        findViewById<View>(R.id.splashRoot).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}