// HomePage.kt
package com.example.myapplication
import com.google.firebase.FirebaseApp
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {

    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val searchBar = findViewById<EditText>(R.id.search_bar)


        // Set up Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
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


        // Navigate to SearchPage when the EditText is touched
        searchBar.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val intent = Intent(this, SearchPage::class.java)
                startActivity(intent)
            }
            true
        }



        // Setup category RecyclerView
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val categories = listOf(
            Category("Vegetables", R.drawable.vegetables),
            Category("Fruits", R.drawable.shopping),
            Category("Snacks", R.drawable.snacks),
            Category("Sweets", R.drawable.sweets),
            Category("Drinks", R.drawable.drinks)
        )

        categoryRecyclerView.adapter = CategoryAdapter(this, categories) { categoryName ->
            openCategoryProducts(categoryName)
        }

        // Set up click listeners for discounted and recently viewed items
        setUpItemClickListeners()
    }

    private fun openCategoryProducts(categoryName: String) {
        val intent = Intent(this, CategoryProductsActivity::class.java)
        intent.putExtra("CATEGORY_NAME", categoryName)
        startActivity(intent)
    }

    private fun setUpItemClickListeners() {
        findViewById<ImageView>(R.id.item1).setOnClickListener {
            openItemInfo("Broccoli", 1.20, R.drawable.broccoli)
        }
        findViewById<ImageView>(R.id.item2).setOnClickListener {
            openItemInfo("Apple", 0.70, R.drawable.apple_image)
        }
        findViewById<ImageView>(R.id.item3).setOnClickListener {
            openItemInfo("Orange", 0.50, R.drawable.orange_image)
        }
        findViewById<ImageView>(R.id.item4).setOnClickListener {
            openItemInfo("Doughnut", 1.30, R.drawable.doughnut)
        }
        findViewById<ImageView>(R.id.item5).setOnClickListener {
            openItemInfo("Grapes", 1.70, R.drawable.grapes_image)
        }

        findViewById<ImageView>(R.id.recent1).setOnClickListener {
            openItemInfo("Broccoli", 1.20, R.drawable.broccoli)
        }
        findViewById<ImageView>(R.id.recent2).setOnClickListener {
            openItemInfo("Corn", 0.60, R.drawable.corn)
        }
        findViewById<ImageView>(R.id.recent3).setOnClickListener {
            openItemInfo("Carrot", 0.89, R.drawable.carrot)
        }
        findViewById<ImageView>(R.id.recent4).setOnClickListener {
            openItemInfo("Grapes", 1.70, R.drawable.grapes_image)
        }
        findViewById<ImageView>(R.id.recent5).setOnClickListener {
            openItemInfo("Orange", 0.50, R.drawable.orange_image)
        }
    }

    private fun openItemInfo(name: String, price: Double, imageResId: Int) {
        val intent = Intent(this, ItemInfoActivity::class.java).apply {
            putExtra("productName", name)
            putExtra("productPrice", price)
            putExtra("productImageResId", imageResId)
        }
        startActivity(intent)
    }
}
