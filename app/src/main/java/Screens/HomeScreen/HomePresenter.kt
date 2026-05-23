package Screens.HomeScreen

import database.CartManager
import database.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.Product
import models.ProductRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val scope: CoroutineScope
) : HomeContract.Presenter {

    override fun startObservingUser() {
        scope.launch {
            UserSession.userState.collectLatest { user ->
                view.displayUsername(user.username)
            }
        }
    }

    override fun loadProducts(category: String) {
        val products = if (category == "All") {
            ProductRepository.products
        } else {
            ProductRepository.products.filter { it.category == category }
        }
        view.displayProducts(products)
    }

    override fun onAddToCartClicked(product: Product) {
        CartManager.addToCart(product)
        view.showCartMessage("${product.name} added to cart")
    }

    override fun onProfileClicked() {
        view.navigateToProfile()
    }

    override fun onCartClicked() {
        view.navigateToCart()
    }
}
