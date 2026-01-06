//CategoryProductsActivity.kt
package com.example.myapplication
import com.google.firebase.FirebaseApp
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CategoryProductsActivity : AppCompatActivity() {

    // Define the list of products
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
        Product("Mushroom", R.drawable.mushroom, 0.45, "Vegetables"),
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
        setContentView(R.layout.activity_category_items)

        // Retrieve and display the category name
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Unknown Category"
        val categoryTitle = findViewById<TextView>(R.id.categoryTitle)
        categoryTitle.text = categoryName

        // Set up the RecyclerView with a GridLayoutManager
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)  // Set to 2 columns

        // Filter products by the selected category
        val filteredProducts = productList.filter { it.category == categoryName }
        recyclerView.adapter = ProductAdapter(filteredProducts)

        // Back Button Functionality
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Updated to use onBackPressedDispatcher
        }

        // Bottom Navigation Functionality
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_home // Set the default selection

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
}
