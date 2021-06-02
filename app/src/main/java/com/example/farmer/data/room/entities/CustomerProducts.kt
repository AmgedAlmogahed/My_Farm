package com.example.farmer.data.room.entities

data class CustomerProducts(
    var farmer_name: String,
    var phone_number:String,
    var whats_app_number:String,
    var state:String,
    var district:String,
    var title: String,
    var price: String,
    var quality: String,
    var stock: String,
    var unit: String,
    val farmer_id: Int,
    var id: Int
)
