package Screens.CartScreen

import database.CartManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.Product
import java.util.Locale

class CartPresenter(
    private val view: CartContract.View,
    private val scope: CoroutineScope
) : CartContract.Presenter {

    override fun startObservingCart() {
        scope.launch {
            CartManager.cartItems.collectLatest { items ->
                view.displayCartItems(items)
                val total = items.sumOf { it.price }
                view.updateTotalPrice(String.format(Locale.getDefault(), "₱%.0f", total))
            }
        }
    }

    override fun onRemoveClicked(product: Product) {
        CartManager.removeFromCart(product)
    }

    override fun onCheckoutClicked() {
        if (CartManager.cartItems.value.isNotEmpty()) {
            view.showMessage("Order placed successfully!")
            CartManager.clearCart()
        } else {
            view.showMessage("Your cart is empty")
        }
    }

    override fun onBackClicked() {
        view.finishView()
    }
}
