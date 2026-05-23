package Screens.ProfileScreen

import Screens.SplashScreen.SplashActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.habiceb_ares_finalproject.R
import database.UserSession
import models.PostRepository
import models.UserPost

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvProfileTitle: TextView
    private lateinit var editSection: LinearLayout
    private lateinit var etNewUsername: EditText
    private lateinit var postGrid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvUsername = findViewById(R.id.tvProfileUser)
        tvProfileTitle = findViewById(R.id.tvProfileTitle)
        editSection = findViewById(R.id.editProfileSection)
        etNewUsername = findViewById(R.id.etNewUsername)
        postGrid = findViewById(R.id.postGrid)

        updateUserUI()

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            editSection.visibility = if (editSection.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            val newName = etNewUsername.text.toString()
            if (newName.isNotEmpty()) {
                UserSession.updateUsername(newName)
                updateUserUI()
                editSection.visibility = View.GONE
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            }
        }

        setupPostGrid()
        setupNavigation()
    }

    private fun updateUserUI() {
        val name = UserSession.username
        tvUsername.text = name
        tvProfileTitle.text = name
    }

    private fun setupPostGrid() {
        val inflater = LayoutInflater.from(this)
        val posts = PostRepository.userPosts
        
        posts.forEach { post ->
            val itemView = inflater.inflate(R.layout.item_post_grid, postGrid, false) as ImageView
            itemView.setImageResource(post.imageRes)
            
            // Layout params for 1/3 width
            val screenWidth = resources.displayMetrics.widthPixels
            val itemSize = screenWidth / 3
            itemView.layoutParams = GridLayout.LayoutParams().apply {
                width = itemSize
                height = itemSize
            }

            itemView.setOnClickListener { showPostDetail(post) }
            postGrid.addView(itemView)
        }
    }

    private fun showPostDetail(post: UserPost) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_post_detail, null)
        val dialog = AlertDialog.Builder(this, R.style.Theme_HABICEB_ARES_FINALPROJECT)
            .setView(dialogView)
            .create()

        dialogView.findViewById<ImageView>(R.id.ivDetailImage).setImageResource(post.imageRes)
        dialogView.findViewById<TextView>(R.id.tvDetailLikes).text = "${post.likes} likes"
        dialogView.findViewById<TextView>(R.id.tvDetailCaption).text = post.caption

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupNavigation() {
        findViewById<ImageButton>(R.id.navHome).setOnClickListener { 
            finish() 
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        findViewById<ImageButton>(R.id.navCart).setOnClickListener {
            startActivity(Intent(this, Screens.CartScreen.CartActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}
