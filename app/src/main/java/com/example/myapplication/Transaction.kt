package com.example.myapplication

data class Transaction(
    val id: String = "",
    val date: String = "",
    val totalAmount: Double = 0.0,
    val items: List<TransactionItem> = listOf()
)
