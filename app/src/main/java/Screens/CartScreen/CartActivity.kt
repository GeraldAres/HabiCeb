package Screens.CartScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.habiceb_ares_finalproject.R
import database.CartManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.Product

class CartActivity : AppCompatActivity() {

    private lateinit var cartItemsContainer: LinearLayout
    private lateinit var tvCartTotal: TextView
    private lateinit var tvEmptyCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartItemsContainer = findViewById(R.id.cartItemsContainer)
        tvCartTotal = findViewById(R.id.tvCartTotal)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)

        findViewById<ImageButton>(R.id.btnBackFromCart).setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        findViewById<Button>(R.id.btnCheckout).setOnClickListener {
            if (CartManager.cartItems.value.isNotEmpty()) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()
                CartManager.clearCart()
            } else {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Reactive update for cart items
        lifecycleScope.launch {
            CartManager.cartItems.collectLatest { items ->
                updateCartUI(items)
            }
        }
    }

    private fun updateCartUI(items: List<Product>) {
        cartItemsContainer.removeAllViews()
        
        if (items.isEmpty()) {
            tvEmptyCart.visibility = View.VISIBLE
            tvCartTotal.text = "₱0.00"
            return
        }

        tvEmptyCart.visibility = View.GONE
        var total = 0.0
        val inflater = LayoutInflater.from(this)

        items.forEach { product ->
            val itemView = inflater.inflate(R.layout.item_cart, cartItemsContainer, false)
            
            itemView.findViewById<TextView>(R.id.tvCartProductName).text = product.name
            itemView.findViewById<TextView>(R.id.tvCartProductBrand).text = product.brand
            itemView.findViewById<TextView>(R.id.tvCartProductPrice).text = "₱${String.format("%.0f", product.price)}"
            
            itemView.findViewById<ImageView>(R.id.ivCartProduct).setImageResource(product.imageRes)

            itemView.findViewById<ImageButton>(R.id.btnRemoveFromCart).setOnClickListener {
                CartManager.removeFromCart(product)
            }

            cartItemsContainer.addView(itemView)
            total += product.price
        }

        tvCartTotal.text = "₱${String.format("%.0f", total)}"
    }
}
