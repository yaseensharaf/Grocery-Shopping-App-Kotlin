package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchPage : AppCompatActivity() {
    private lateinit var productAdapter: ProductAdapter
    private val productList = listOf(
        Product("Apple", R.drawable.apple_image, 0.70, "Fruits"),
        Product("Orange", R.drawable.orange_image, 0.50, "Fruits"),
        Product("Banana", R.drawable.banana_image, 0.80, "Fruits"),
        Product("Broccoli", R.drawable.broccoli, 1.20, "Vegetables"),
        Product("Carrot", R.drawable.carrot, 0.89, "Vegetables"),
        Product("Doughnut", R.drawable.doughnut, 1.30, "Sweets"),
        Product("Corn", R.drawable.corn, 0.60, "Vegetables"),
        Product("soda", R.drawable.soda, 1.50, "Drinks"),
        Product("Potato", R.drawable.potato, 0.75, "Vegetables"),
        Product("Mushroom", R.drawable.mushroom, 0.45, "Vegtables"),
        Product("Kiwi", R.drawable.kwiwi, 1.29, "Fruits"),
        Product("Lemon", R.drawable.lemon, 0.49, "Fruits"),
        Product("Stawberry", R.drawable.strawberry, 0.79, "Fruits"),
        Product("Kikat", R.drawable.kikat, 1.29, "Snacks"),
        Product("snickers", R.drawable.snickers, 0.90, "Snacks"),
        Product("Twix", R.drawable.twix, 0.50, "Snacks"),
        Product("Twirl", R.drawable.twirl, 0.70, "Snacks"),
        Product("Water", R.drawable.water, 0.99, "Drinks"),
        Product("Redbull", R.drawable.redbull, 2.15, "Drinks"),
        Product("Pepsi", R.drawable.pepsi, 0.89, "Drinks"),
        Product("Grapes", R.drawable.grapes_image, 1.70, "Fruits")
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val backButton = findViewById<ImageView>(R.id.backButton)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Initialize RecyclerView with GridLayoutManager
        productAdapter = ProductAdapter(productList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = productAdapter

        // Implement SearchView functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = productList.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                productAdapter.filterList(filteredList)
                return true
            }
        })

        // Back button functionality
        backButton.setOnClickListener {
            finish() // Close the search page and return to the previous activity
        }

        // Handle BottomNavigationView clicks
        bottomNavigationView.selectedItemId = R.id.nav_search // Highlight search tab
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomePage::class.java))
                    true
                }
                R.id.nav_search -> true // Stay on the current page
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
}
