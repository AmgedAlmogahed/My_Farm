package com.example.farmer.data.network.responses

data class AccountResponse(
    val account:List<Account>,
    val error: Boolean
)