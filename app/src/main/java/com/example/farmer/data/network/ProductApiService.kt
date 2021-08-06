package com.example.farmer.data.network

import com.example.farmer.data.network.responses.AddResponse
import com.example.farmer.data.network.responses.AllProductsResponse
import com.example.farmer.data.network.responses.ProductsResponseItem
import retrofit2.http.*

interface ProductApiService {
    @GET("products")
    suspend fun getAllProducts(): List<AllProductsResponse>

    @GET("products/{id}")
    suspend fun getFramerProducts(@Path("id") id: Int): List<ProductsResponseItem>

    @FormUrlEncoded
    @POST("addproduct")
    suspend fun addProduct(
        @Field("farmer_id") farmerId: Int?,
        @Field("title") title: String?,
        @Field("price") price: String?,
        @Field("stock") stock: String?,
        @Field("unit") unit: String?,
        @Field("quality") quality: String?,
        @Field("status") status: String?,
    ): AddResponse


}
