package com.example.myapplication

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        val existingProduct = cartItems.find { it.name == product.name }
        if (existingProduct != null) {
            existingProduct.quantity += 1
        } else {
            cartItems.add(product.copy(quantity = 1))
        }
    }

    fun getCartItems(): MutableList<Product> = cartItems

    fun getTotalPrice(): Double = cartItems.sumOf { it.price * it.quantity }

    fun clearCart() {
        cartItems.clear()
    }

    fun incrementQuantity(productName: String) {
        val product = cartItems.find { it.name == productName }
        product?.let { it.quantity++ }
    }

    fun decrementQuantity(productName: String) {
        val product = cartItems.find { it.name == productName }
        product?.let {
            it.quantity--
            if (it.quantity <= 0) {
                cartItems.remove(it)
            }
        }
    }
}
