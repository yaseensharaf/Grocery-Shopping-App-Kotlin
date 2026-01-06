// ProductAdapter.kt
package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // A mutable list to hold the filtered items for search functionality
    private var filteredProductList: List<Product> = productList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredProductList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ItemInfoActivity::class.java)
            intent.putExtra("productName", product.name)
            intent.putExtra("productPrice", product.price)
            intent.putExtra("productImageResId", product.imageResId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredProductList.size
    }

    // Function to update the list based on search query
    fun filterList(filteredList: List<Product>) {
        filteredProductList = filteredList
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)

        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = "£${product.price}"
            productImage.setImageResource(product.imageResId)
        }
    }
}
