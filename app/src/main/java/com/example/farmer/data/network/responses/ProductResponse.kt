package com.example.farmer.data.network.responses

data class ProductResponse(
    val created_at: Any,
    val farmer_id: Int,
    val id: Int,
    val price: String,
    val quality: String,
    val status: String,
    val stock: String,
    val title: String,
    val updated_at: Any
)