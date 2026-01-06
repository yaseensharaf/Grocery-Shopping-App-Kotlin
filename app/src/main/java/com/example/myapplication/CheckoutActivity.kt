// CheckoutActivity.kt
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Set total price from CartManager
        val totalTextView = findViewById<TextView>(R.id.checkoutTotalPrice)
        val totalPrice = CartManager.getTotalPrice()
        totalTextView.text = "Total: £${String.format("%.2f", totalPrice)}" // Updated with £ symbol

        // Add click listener to confirm order button
        findViewById<Button>(R.id.confirmOrderButton).setOnClickListener {
            if (totalPrice > 0) {
                // Confirm the order
                Toast.makeText(this, "Order confirmed!", Toast.LENGTH_SHORT).show()

                // Clear the cart after order confirmation
                CartManager.clearCart()

                // Navigate back to the main activity or cart
                val intent = Intent(this, HomePage::class.java) // Replace with CartActivity if desired
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish() // Close CheckoutActivity
            } else {
                // If cart is empty
                Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
