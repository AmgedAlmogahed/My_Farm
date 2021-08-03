package com.example.farmer.data.network

import com.example.farmer.data.network.responses.AddResponse
import com.example.farmer.data.network.responses.ProductResponse
import retrofit2.http.*

interface ProductApiService {
    @FormUrlEncoded
    @GET("products")
    suspend fun getAllProducts(): List<ProductResponse>

    @FormUrlEncoded
    @GET("products/{id}")
    suspend fun getFramerProducts(@Path("id") id: Int): List<ProductResponse>

    @FormUrlEncoded
    @POST("addproduct")
    suspend fun addProduct(
        @Field("farmer_id") farmerId: Int,
        @Field("title") title: String,
        @Field("price") price: String,
        @Field("stock") stock: String,
        @Field("quality") quality: String,
        @Field("status") status: String,
    ): AddResponse


}
