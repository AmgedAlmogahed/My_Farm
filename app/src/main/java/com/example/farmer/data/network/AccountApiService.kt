package com.example.android.tasheel.data.network


import com.example.farmer.data.network.responses.AccountResponse
import com.example.farmer.data.network.responses.RegisterResponse
import retrofit2.http.*


interface AccountApiService {


    @FormUrlEncoded
    @POST("addaccount")
    suspend fun createAccount(
        @Field("name") name: String?,
        @Field("phone_number") phoneNumber: String?,
        @Field("whatsapp_number") watsAppNumber: String?,
        @Field("state") city: String?,
        @Field("pincode") pincode: String?,
        @Field("address") address: String?,
        @Field("type") type: String?
    ): RegisterResponse

    @FormUrlEncoded
    @GET("account/{id}/{type}")
    suspend fun getAccount(@Path("id") id: Int): AccountResponse

    @FormUrlEncoded
    @GET("accounts/{type}")
    suspend fun getAccounts(@Path("type") type: String): AccountResponse


    @GET("account/{phone}")
    suspend fun validateAccount(@Path("phone") phoneNumber: String): AccountResponse



}