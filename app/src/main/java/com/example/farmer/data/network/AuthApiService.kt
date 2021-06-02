package com.example.android.tasheel.data.network



import com.example.farmer.data.responses.LoginResponse
import com.example.farmer.data.responses.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthApiService {

    @FormUrlEncoded
    @POST("userlogin")
    suspend fun login(
            @Field("phone_number") phoneNumber: String,
            @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("createcustomer")
    suspend fun createCustomerAccount(
            @Field("name") name: String,
            @Field("phone_number") phoneNumber: String,
            @Field("email") email: String,
            @Field("city") city: String,
            @Field("password") password: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("valdiateuser")
    suspend fun validateUser(
            @Field("user_name") name: String,
            @Field("phone_number") phoneNumber: String
    ) : RegisterResponse




    @GET("user")
    suspend fun getUser(): LoginResponse

    @POST("logout")
    suspend fun logout(): ResponseBody
}