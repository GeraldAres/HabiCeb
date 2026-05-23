package Screens.CartScreen

import models.Product

interface CartContract {
    interface View {
        fun displayCartItems(items: List<Product>)
        fun updateTotalPrice(total: String)
        fun showMessage(msg: String)
        fun finishView()
    }
    interface Presenter {
        fun startObservingCart()
        fun onRemoveClicked(product: Product)
        fun onCheckoutClicked()
        fun onBackClicked()
    }
}
