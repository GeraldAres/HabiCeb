package Screens.HomeScreen

import models.Product

interface HomeContract {
    interface View {
        fun displayUsername(name: String)
        fun displayProducts(products: List<Product>)
        fun showCartMessage(msg: String)
        fun navigateToProfile()
        fun navigateToCart()
    }
    interface Presenter {
        fun startObservingUser()
        fun loadProducts(category: String = "All")
        fun onAddToCartClicked(product: Product)
        fun onProfileClicked()
        fun onCartClicked()
    }
}
