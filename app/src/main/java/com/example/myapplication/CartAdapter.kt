package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private var cartItems: MutableList<Product>, // Mutable list for dynamic updates
    private val updateTotalPriceCallback: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // Method to update the cart items dynamically
    fun updateCartItems(newCartItems: MutableList<Product>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.cartItemName)
        val productPrice: TextView = itemView.findViewById(R.id.cartItemPrice)
        val quantityText: TextView = itemView.findViewById(R.id.cartItemQuantity)
        val minusButton: Button = itemView.findViewById(R.id.minusButton)
        val plusButton: Button = itemView.findViewById(R.id.plusButton)
        val productImage: ImageView = itemView.findViewById(R.id.cartItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]

        holder.productName.text = product.name
        holder.productPrice.text = "£${String.format("%.2f", product.price)}"
        holder.quantityText.text = product.quantity.toString()
        holder.productImage.setImageResource(product.imageResId)

        holder.plusButton.setOnClickListener {
            CartManager.incrementQuantity(product.name)
            notifyItemChanged(position)
            updateTotalPriceCallback()
        }

        holder.minusButton.setOnClickListener {
            CartManager.decrementQuantity(product.name)
            if (product.quantity <= 0) {
                cartItems.removeAt(position) // Remove the product if quantity is 0
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItems.size) // Refresh the remaining items
            } else {
                notifyItemChanged(position)
            }
            updateTotalPriceCallback()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
