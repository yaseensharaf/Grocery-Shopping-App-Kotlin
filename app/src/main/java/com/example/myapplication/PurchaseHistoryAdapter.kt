package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class PurchaseHistoryAdapter(
    private val transactions: List<Transaction>
) : RecyclerView.Adapter<PurchaseHistoryAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionIdTextView: TextView = itemView.findViewById(R.id.transactionIdTextView)
        val transactionDateTextView: TextView = itemView.findViewById(R.id.transactionDateTextView)
        val transactionTotalTextView: TextView = itemView.findViewById(R.id.transactionTotalTextView)
        val itemsTextView: TextView = itemView.findViewById(R.id.transactionItemsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.transactionIdTextView.text = "Transaction ID: ${transaction.id}"

        // Convert timestamp to a readable date
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(transaction.date.toLongOrNull() ?: 0L)
        holder.transactionDateTextView.text = "Date: ${sdf.format(date)}"

        holder.transactionTotalTextView.text = "Total: £${String.format("%.2f", transaction.totalAmount)}"

        // Display the list of items in a single string
        val itemsDetails = transaction.items.joinToString(separator = "\n") {
            "${it.name} x${it.quantity} (£${String.format("%.2f", it.price)})"
        }
        holder.itemsTextView.text = itemsDetails
    }

    override fun getItemCount(): Int = transactions.size
}
