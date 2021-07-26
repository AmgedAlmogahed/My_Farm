package com.example.farmer.data.network

import com.example.farmer.data.responses.AddResponse
import com.example.farmer.data.responses.FeedbackResponse
import com.example.farmer.data.responses.ProductResponse
import retrofit2.http.*

interface FeedbackApiService {


    @FormUrlEncoded
    @GET("products/{id}")
    suspend fun getFramerProducts(@Path("id") id: String): List<FeedbackResponse>

    @FormUrlEncoded
    @POST("addcomment")
    suspend fun addProduct(
        @Field("farmer_id") farmerId: String,
        @Field("title") title: String,
    ): AddResponse


}