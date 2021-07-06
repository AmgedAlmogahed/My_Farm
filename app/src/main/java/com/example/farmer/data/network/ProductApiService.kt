package com.example.farmer.data.network

import com.example.farmer.data.responses.Products
import retrofit2.http.*

interface ProductApiService {

    @GET("products")
    suspend fun getAllProducts(): List<Products>

    @POST
    suspend fun addProduct(): Nothing

    @PUT
    suspend fun updateProduct(): Nothing

    @DELETE
    suspend fun deleteProduct(): Nothing
}
