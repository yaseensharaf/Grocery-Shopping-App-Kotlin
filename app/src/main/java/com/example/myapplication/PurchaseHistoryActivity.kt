package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PurchaseHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noHistoryTextView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Link UI elements
        recyclerView = findViewById(R.id.purchaseHistoryRecyclerView)
        noHistoryTextView = findViewById(R.id.noHistoryTextView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load Purchase History
        loadPurchaseHistory()

        // Bottom Navigation View setup
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
    }

    private fun loadPurchaseHistory() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.reference.child("purchase_history").child(userId).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val transactions = mutableListOf<Transaction>()
                        snapshot.children.forEach { transactionSnapshot ->
                            val transaction = transactionSnapshot.getValue(Transaction::class.java)
                            transaction?.let { transactions.add(it) }
                        }
                        if (transactions.isNotEmpty()) {
                            recyclerView.adapter = PurchaseHistoryAdapter(transactions)
                            noHistoryTextView.visibility = View.GONE
                        } else {
                            noHistoryTextView.visibility = View.VISIBLE
                        }
                    } else {
                        noHistoryTextView.visibility = View.VISIBLE
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load purchase history", Toast.LENGTH_SHORT).show()
                    noHistoryTextView.visibility = View.VISIBLE
                }
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            noHistoryTextView.visibility = View.VISIBLE
        }
    }
}
