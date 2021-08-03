package com.example.farmer.data.network.repository

import com.example.farmer.data.network.FeedbackApi
import com.example.farmer.data.network.ProductsApi
import com.example.farmer.data.room.dao.ProductsDao
import com.example.farmer.data.room.entities.Products

class Repository(private val dao: ProductsDao? = null) {


    //Local Database functions


    fun getAllProductsL() = dao?.getAllProducts()

    //fun getFarmerProducts(key: Long) = dao?.getAllProducts(key)

    suspend fun addProduct(product: Products) = dao?.insert(product)

    suspend fun signOut() = dao?.clearAccounts()

    suspend fun getPhoneNumber() = dao?.validateFarmer()

    suspend fun getId(phoneNumber: String) = dao?.get(phoneNumber)

    fun getAccount() = dao?.getAllAccounts()

    //Network layer Functions

    //products functions
    /**
     * this function returns all the products of all farmers
     */
    suspend fun getAllProducts() =
        ProductsApi.RETROFIT_SERVICE.getAllProducts()

    /**
     * this function accept 6 parameters to add products
     */
    suspend fun addProduct(
        id: Int,
        title: String,
        price: String,
        stock: String,
        quality: String,
        status: String
    ) =
        ProductsApi.RETROFIT_SERVICE.addProduct(
            id,
            title,
            price,
            stock,
            quality,
            status
        )

    /**
     * this function return the products of specific farmer by passing his id as an argument
     */
    suspend fun getFarmerProducts(id: Int) =
        ProductsApi.RETROFIT_SERVICE.getFramerProducts(id)


    //Feedback functions

    suspend fun addFeedback(productId:Int, customerId: Int, name: String) =
        FeedbackApi.RETROFIT_SERVICE.addComment(productId, customerId, name)

    suspend fun getFeedback(productId : Int) =
        FeedbackApi.RETROFIT_SERVICE.getFeedbackForProduct(productId)

}