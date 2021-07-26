package com.example.farmer.data.responses

data class AccountResponse(
    val address: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val pincode: String,
    val state: String,
    val type: String,
    val whatsapp_number: String
)