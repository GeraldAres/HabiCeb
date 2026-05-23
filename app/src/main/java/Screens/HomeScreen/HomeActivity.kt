package Screens.HomeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.habiceb_ares_finalproject.R
import Screens.ProfileScreen.ProfileActivity
import Screens.CartScreen.CartActivity
import models.Product
import models.ProductRepository
import java.util.Locale

class HomeActivity : AppCompatActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var categoryContainer: LinearLayout
    private lateinit var productContainer: LinearLayout
    private lateinit var tvHomeUser: TextView
    private var selectedCategoryView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter = HomePresenter(this, lifecycleScope)

        categoryContainer = findViewById(R.id.categoryContainer)
        productContainer = findViewById(R.id.productContainer)
        tvHomeUser = findViewById(R.id.tvHomeUser)

        setupCategories()
        presenter.startObservingUser()
        presenter.loadProducts()
        setupNavigation()

        applyFadeInAnimation(findViewById(R.id.headerHome))
    }

    override fun displayUsername(name: String) {
        tvHomeUser.text = name
    }

    override fun displayProducts(products: List<Product>) {
        productContainer.removeAllViews()
        val inflater = LayoutInflater.from(this)
        products.forEachIndexed { index, product ->
            val productView = inflater.inflate(R.layout.item_product, productContainer, false)

            productView.findViewById<TextView>(R.id.tvBrand).text = product.brand
            productView.findViewById<TextView>(R.id.tvProductName).text = product.name
            productView.findViewById<TextView>(R.id.tvPrice).text = String.format(Locale.getDefault(), "₱%.0f", product.price)
            productView.findViewById<ImageView>(R.id.ivProduct).setImageResource(product.imageRes)

            val ivStar = productView.findViewById<ImageView>(R.id.ivStar)
            var isStarred = false
            ivStar.setOnClickListener {
                isStarred = !isStarred
                if (isStarred) {
                    ivStar.setImageResource(android.R.drawable.btn_star_big_on)
                    ivStar.setColorFilter(android.graphics.Color.YELLOW)
                } else {
                    ivStar.setImageResource(android.R.drawable.btn_star_big_off)
                    ivStar.setColorFilter(android.graphics.Color.GRAY)
                }
                applyBounceAnimation(it)
            }

            productView.findViewById<ImageButton>(R.id.btnAddToCart).setOnClickListener {
                presenter.onAddToCartClicked(product)
                applyBounceAnimation(it)
            }

            productContainer.addView(productView)
            applyFadeInAnimation(productView, index * 100L)
        }
    }

    override fun showCartMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun navigateToCart() {
        startActivity(Intent(this, CartActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun setupCategories() {
        val inflater = LayoutInflater.from(this)
        ProductRepository.categories.forEach { category ->
            val categoryView = inflater.inflate(R.layout.item_category, categoryContainer, false) as TextView
            categoryView.text = category

            if (category == "All") {
                selectCategory(categoryView)
            }

            categoryView.setOnClickListener {
                selectCategory(categoryView)
                presenter.loadProducts(category)
            }
            categoryContainer.addView(categoryView)
        }
    }

    private fun selectCategory(view: TextView) {
        selectedCategoryView?.isSelected = false
        view.isSelected = true
        selectedCategoryView = view

        val scaleUp = ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleUp.duration = 200
        view.startAnimation(scaleUp)
    }

    private fun applyFadeInAnimation(view: View, delay: Long = 0) {
        val fade = AlphaAnimation(0f, 1f)
        fade.duration = 500
        fade.startOffset = delay
        view.startAnimation(fade)
    }

    private fun applyBounceAnimation(view: View) {
        val bounce = ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        bounce.duration = 100
        bounce.repeatMode = Animation.REVERSE
        bounce.repeatCount = 1
        view.startAnimation(bounce)
    }

    private fun setupNavigation() {
        findViewById<ImageButton>(R.id.navProfile).setOnClickListener { presenter.onProfileClicked() }
        findViewById<ImageButton>(R.id.navCart).setOnClickListener { presenter.onCartClicked() }
    }
}
