// Product.kt
package com.example.myapplication

data class Product(
    val name: String,           // Product name
    val imageResId: Int,        // Image resource ID
    val price: Double,          // Price of the product
    val category: String,       // Category name
    var quantity: Int = 1       // Quantity with default value 1
)
