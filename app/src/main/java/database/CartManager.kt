package database

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.Product

object CartManager {
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        val currentList = _cartItems.value.toMutableList()
        currentList.add(product)
        _cartItems.value = currentList
    }

    fun removeFromCart(product: Product) {
        val currentList = _cartItems.value.toMutableList()
        currentList.remove(product)
        _cartItems.value = currentList
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
