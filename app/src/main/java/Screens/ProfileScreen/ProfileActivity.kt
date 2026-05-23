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
import models.UserPost

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private lateinit var presenter: ProfileContract.Presenter
    private lateinit var tvUsername: TextView
    private lateinit var tvProfileTitle: TextView
    private lateinit var editSection: LinearLayout
    private lateinit var etNewUsername: EditText
    private lateinit var postGrid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenter = ProfilePresenter(this)

        tvUsername = findViewById(R.id.tvProfileUser)
        tvProfileTitle = findViewById(R.id.tvProfileTitle)
        editSection = findViewById(R.id.editProfileSection)
        etNewUsername = findViewById(R.id.etNewUsername)
        postGrid = findViewById(R.id.postGrid)

        findViewById<Button>(R.id.btnLogout).setOnClickListener { presenter.onLogoutClicked() }
        findViewById<Button>(R.id.btnEditProfile).setOnClickListener { presenter.onEditClicked() }
        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener { presenter.onSaveClicked(etNewUsername.text.toString()) }

        findViewById<ImageButton>(R.id.navHome).setOnClickListener { presenter.onHomeClicked() }
        findViewById<ImageButton>(R.id.navCart).setOnClickListener { presenter.onCartClicked() }

        presenter.init()
    }

    override fun updateUserInfo(name: String) {
        tvUsername.text = name
        tvProfileTitle.text = name
    }

    override fun displayPosts(posts: List<UserPost>) {
        postGrid.removeAllViews()
        val inflater = LayoutInflater.from(this)
        val screenWidth = resources.displayMetrics.widthPixels
        val itemSize = screenWidth / 3

        posts.forEach { post ->
            val itemView = inflater.inflate(R.layout.item_post_grid, postGrid, false) as ImageView
            itemView.setImageResource(post.imageRes)
            itemView.layoutParams = GridLayout.LayoutParams().apply {
                width = itemSize
                height = itemSize
            }
            itemView.setOnClickListener { presenter.onPostClicked(post) }
            postGrid.addView(itemView)
        }
    }

    override fun showPostDetail(post: UserPost) {
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

    override fun toggleEditSection(show: Boolean) {
        editSection.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun navigateToCart() {
        startActivity(Intent(this, Screens.CartScreen.CartActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun navigateToSplash() {
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
