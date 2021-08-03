package com.example.farmer.data.network.responses

data class FeedbackResponse(
    val comment: String,
    val customer_id: Int,
    val id: Int,
    val product_id: Int
)