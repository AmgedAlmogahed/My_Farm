package com.example.farmer.data.network.responses

data class RegisterResponse(
    val account: List<Account>,
    val error: Boolean,
    val message: String
)