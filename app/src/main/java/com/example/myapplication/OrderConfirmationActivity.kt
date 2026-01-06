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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OrderConfirmationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // UI elements
        val customerNameTextView = findViewById<TextView>(R.id.customerName)
        val customerAddressTextView = findViewById<TextView>(R.id.customerAddress)
        val customerPhoneTextView = findViewById<TextView>(R.id.customerPhoneNumber)
        val totalTextView = findViewById<TextView>(R.id.orderTotalTextView)

        // Set up RecyclerView
        val orderSummaryRecyclerView = findViewById<RecyclerView>(R.id.orderSummaryRecyclerView)
        orderSummaryRecyclerView.layoutManager = LinearLayoutManager(this)
        orderSummaryRecyclerView.adapter = OrderSummaryAdapter(CartManager.getCartItems())

        // Display total price
        val totalPrice = CartManager.getTotalPrice()
        totalTextView.text = "Total: £${String.format("%.2f", totalPrice)}"

        // Fetch user details from Firebase
        fetchUserDetails(customerNameTextView, customerAddressTextView, customerPhoneTextView)

        // Handle Confirm Order button click
        findViewById<Button>(R.id.confirmOrderButton).setOnClickListener {
            savePurchaseHistory()
            Toast.makeText(this, "Order confirmed! Thank you for your purchase.", Toast.LENGTH_LONG).show()
            CartManager.clearCart()

            // Navigate back to the main screen
            val intent = Intent(this, HomePage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Back button functionality
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        // Bottom Navigation Functionality
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_cart

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomePage::class.java))
                    true
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchPage::class.java))
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchUserDetails(
        nameTextView: TextView,
        addressTextView: TextView,
        phoneTextView: TextView
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java) ?: "N/A"
                        val address = snapshot.child("address").getValue(String::class.java) ?: "N/A"
                        val phone = snapshot.child("phone").getValue(String::class.java) ?: "N/A"

                        nameTextView.text = "Customer Name: $name"
                        addressTextView.text = "Delivering to: $address"
                        phoneTextView.text = "Phone Number: $phone"
                    } else {
                        nameTextView.text = "Customer Name: N/A"
                        addressTextView.text = "Delivering to: N/A"
                        phoneTextView.text = "Phone Number: N/A"
                    }
                }
                .addOnFailureListener {
                    nameTextView.text = "Customer Name: N/A"
                    addressTextView.text = "Delivering to: N/A"
                    phoneTextView.text = "Phone Number: N/A"
                    Toast.makeText(this, "Failed to load user details.", Toast.LENGTH_SHORT).show()
                }
        } else {
            nameTextView.text = "Customer Name: N/A"
            addressTextView.text = "Delivering to: N/A"
            phoneTextView.text = "Phone Number: N/A"
        }
    }

    private fun savePurchaseHistory() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val transactionId = database.reference.child("purchase_history").child(userId).push().key
            if (transactionId != null) {
                // Convert CartManager items to TransactionItem
                val transactionItems = CartManager.getCartItems().map { product ->
                    TransactionItem(
                        name = product.name,
                        price = product.price,
                        quantity = product.quantity
                    )
                }
                val transaction = Transaction(
                    id = transactionId,
                    date = System.currentTimeMillis().toString(),
                    totalAmount = CartManager.getTotalPrice(),
                    items = transactionItems // Pass the mapped items
                )
                database.reference.child("purchase_history").child(userId).child(transactionId)
                    .setValue(transaction)
            }
        }
    }
}
