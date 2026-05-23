package Screens.CartScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.habiceb_ares_finalproject.R
import models.Product
import java.util.Locale

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartItemsContainer: LinearLayout
    private lateinit var tvCartTotal: TextView
    private lateinit var tvEmptyCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        presenter = CartPresenter(this, lifecycleScope)

        cartItemsContainer = findViewById(R.id.cartItemsContainer)
        tvCartTotal = findViewById(R.id.tvCartTotal)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)

        findViewById<ImageButton>(R.id.btnBackFromCart).setOnClickListener { presenter.onBackClicked() }
        findViewById<Button>(R.id.btnCheckout).setOnClickListener { presenter.onCheckoutClicked() }

        presenter.startObservingCart()
    }

    override fun displayCartItems(items: List<Product>) {
        cartItemsContainer.removeAllViews()
        if (items.isEmpty()) {
            tvEmptyCart.visibility = View.VISIBLE
            return
        }
        tvEmptyCart.visibility = View.GONE

        val inflater = LayoutInflater.from(this)
        items.forEach { product ->
            val itemView = inflater.inflate(R.layout.item_cart, cartItemsContainer, false)
            itemView.findViewById<TextView>(R.id.tvCartProductName).text = product.name
            itemView.findViewById<TextView>(R.id.tvCartProductBrand).text = product.brand
            itemView.findViewById<TextView>(R.id.tvCartProductPrice).text = String.format(Locale.getDefault(), "₱%.0f", product.price)
            itemView.findViewById<ImageView>(R.id.ivCartProduct).setImageResource(product.imageRes)

            itemView.findViewById<ImageButton>(R.id.btnRemoveFromCart).setOnClickListener {
                presenter.onRemoveClicked(product)
            }
            cartItemsContainer.addView(itemView)
        }
    }

    override fun updateTotalPrice(total: String) {
        tvCartTotal.text = total
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun finishView() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
