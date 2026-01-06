package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalTextView: TextView
    private lateinit var clearCartButton: Button
    private lateinit var checkoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Initialize UI elements
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalPriceTextView)
        clearCartButton = findViewById(R.id.clearCartButton)
        checkoutButton = findViewById(R.id.proceedToCheckoutButton)
        val backButton = findViewById<ImageView>(R.id.backButton)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(CartManager.getCartItems().toMutableList(), ::updateTotalPrice)
        recyclerView.adapter = cartAdapter

        // Display total price and update cart state
        updateTotalPrice()
        checkCartState()

        // Back button functionality
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Updated to use onBackPressedDispatcher
        }

        // Clear cart functionality
        clearCartButton.setOnClickListener {
            CartManager.clearCart()
            cartAdapter.updateCartItems(CartManager.getCartItems()) // Update adapter with empty list
            updateTotalPrice()
            checkCartState()
            Toast.makeText(this, "Cart cleared", Toast.LENGTH_SHORT).show()
        }

        // Proceed to checkout
        checkoutButton.setOnClickListener {
            if (CartManager.getCartItems().isEmpty()) {
                Toast.makeText(this, "Your cart is empty. Add items to proceed!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, OrderConfirmationActivity::class.java)
                startActivity(intent)
            }
        }

        // Set up Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_cart

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomePage::class.java))
                    true
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchPage::class.java))
                    true
                }
                R.id.nav_cart -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = CartManager.getTotalPrice()
        totalTextView.text = "Total: £${String.format("%.2f", totalPrice)}"
    }

    private fun checkCartState() {
        val isCartEmpty = CartManager.getCartItems().isEmpty()
        checkoutButton.isEnabled = !isCartEmpty
        checkoutButton.alpha = if (isCartEmpty) 0.5f else 1.0f // Dim button when disabled
    }

    override fun onResume() {
        super.onResume()
        cartAdapter.notifyDataSetChanged()
        updateTotalPrice()
        checkCartState()
    }
}
