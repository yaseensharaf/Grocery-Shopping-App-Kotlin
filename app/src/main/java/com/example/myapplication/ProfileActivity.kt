package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPhone: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnViewHistory: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Link UI elements
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvAddress = findViewById(R.id.tvAddress)
        tvPhone = findViewById(R.id.tvPhone)
        btnLogout = findViewById(R.id.btnLogout)
        btnViewHistory = findViewById(R.id.btnViewHistory)
        progressBar = findViewById(R.id.progressBar)

        // Bottom Navigation View
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_profile

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
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }

        // Show ProgressBar while loading
        progressBar.visibility = View.VISIBLE

        // Fetch and display user data
        fetchUserData()

        // Logout functionality
        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // View Purchase History functionality
        btnViewHistory.setOnClickListener {
            startActivity(Intent(this, PurchaseHistoryActivity::class.java))
        }
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("users").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    progressBar.visibility = View.GONE // Hide ProgressBar
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java) ?: "N/A"
                        val email = snapshot.child("email").getValue(String::class.java) ?: "N/A"
                        val address = snapshot.child("address").getValue(String::class.java) ?: "N/A"
                        val phone = snapshot.child("phone").getValue(String::class.java) ?: "N/A"

                        tvName.text = "Name: $name"
                        tvEmail.text = "Email: $email"
                        tvAddress.text = "Address: $address"
                        tvPhone.text = "Phone: $phone"
                    } else {
                        tvName.text = "No user data found."
                        tvEmail.text = ""
                        tvAddress.text = ""
                        tvPhone.text = ""
                    }
                }
                .addOnFailureListener {
                    progressBar.visibility = View.GONE // Hide ProgressBar
                    tvName.text = "Failed to load user details."
                    Toast.makeText(this, "Error fetching user details.", Toast.LENGTH_SHORT).show()
                }
        } else {
            progressBar.visibility = View.GONE // Hide ProgressBar
            tvName.text = "No user is logged in."
        }
    }
}
