//ItemInfoActivity.kt
package com.example.myapplication
import com.google.firebase.FirebaseApp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.CartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ItemInfoActivity : AppCompatActivity() {

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)

        // Retrieve product details from intent
        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productImageResId = intent.getIntExtra("productImageResId", 0)
        val productDescription = intent.getStringExtra("productDescription") ?: "No description available"

        product = Product(productName ?: "", productImageResId, productPrice, "", 1)

        // Set product details in UI
        findViewById<TextView>(R.id.ItemTitle).text = productName
        findViewById<TextView>(R.id.ItemPrice).text = "£${productPrice}"
        findViewById<ImageView>(R.id.productImage).setImageResource(productImageResId)
        findViewById<TextView>(R.id.ItemDescription).text = productDescription

        // Set up back button
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Updated to use onBackPressedDispatcher
        }

        // Set up buttons
        val btnAddToCart = findViewById<Button>(R.id.btnCart)
        val btnGoToCart = findViewById<Button>(R.id.btnBuy)

        btnAddToCart.setOnClickListener {
            CartManager.addToCart(product)
            Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        btnGoToCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Set up Bottom Navigation
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
}
