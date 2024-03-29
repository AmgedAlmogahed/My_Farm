package com.example.farmer.data.network

import com.example.farmer.data.network.responses.AddResponse
import com.example.farmer.data.network.responses.FeedbackResponse
import retrofit2.http.*

interface FeedbackApiService {



    @GET("comments/{id}")
    suspend fun getFeedbackForProduct(@Path("id") id: Int): List<FeedbackResponse>

    @FormUrlEncoded
    @POST("addcomment")
    suspend fun addComment(
        @Field("product_id") productId: Int,
        @Field("customer_id") customerId: Int?,
        @Field("comment") title: String?,
        @Field("name") name: String?,
    ): AddResponse


}