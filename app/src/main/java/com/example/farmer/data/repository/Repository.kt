package com.example.farmer.data.repository

import com.example.farmer.data.network.ListingApi
import com.example.farmer.data.network.ListingApiService
import com.example.farmer.data.room.dao.ProductsDao
import com.example.farmer.data.room.entities.Products

class Repository(private val dao : ProductsDao? = null) {

    suspend fun getAllProducts() =
        ListingApi.retrofitService.getAllProducts()

     fun getAllProductsL() = dao?.getAllProducts()

    fun getFarmerProducts(key : Long) = dao?.getAllProducts(key)

    suspend fun addProduct(product: Products) = dao?.insert(product)

    suspend fun signOut() = dao?.clearAccounts()

    suspend fun getPhoneNumber() = dao?.validateFarmer()

    suspend fun getId(phoneNumber: String) = dao?.get(phoneNumber)



}