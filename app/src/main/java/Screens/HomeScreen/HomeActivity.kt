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
import database.CartManager
import database.UserSession
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.Product
import models.ProductRepository
import Screens.ProfileScreen.ProfileActivity
import Screens.CartScreen.CartActivity
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var categoryContainer: LinearLayout
    private lateinit var productContainer: LinearLayout
    private var selectedCategoryView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        categoryContainer = findViewById(R.id.categoryContainer)
        productContainer = findViewById(R.id.productContainer)

        val tvHomeUser = findViewById<TextView>(R.id.tvHomeUser)

        lifecycleScope.launch {
            UserSession.userState.collectLatest { user ->
                tvHomeUser.text = user.username
            }
        }

        setupCategories()
        filterProducts("All")
        setupNavigation()
        
        applyFadeInAnimation(findViewById(R.id.headerHome))
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
                filterProducts(category)
            }
            categoryContainer.addView(categoryView)
        }
    }

    private fun selectCategory(view: TextView) {
        selectedCategoryView?.isSelected = false
        view.isSelected = true
        selectedCategoryView = view
        
        // Animation
        val scaleUp = ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleUp.duration = 200
        view.startAnimation(scaleUp)
    }

    private fun filterProducts(category: String) {
        productContainer.removeAllViews()
        val products = if (category == "All") {
            ProductRepository.products
        } else {
            ProductRepository.products.filter { it.category == category }
        }
        
        displayProducts(products)
    }

    private fun displayProducts(products: List<Product>) {
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
                CartManager.addToCart(product)
                Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                applyBounceAnimation(it)
            }

            productContainer.addView(productView)
            applyFadeInAnimation(productView, index * 100L)
        }
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
        findViewById<ImageButton>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        findViewById<ImageButton>(R.id.navCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}
