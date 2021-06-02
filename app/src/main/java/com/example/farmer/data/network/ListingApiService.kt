package com.example.farmer.data.network

import com.example.farmer.data.responses.Listing
import retrofit2.http.*

interface ListingApiService {

    @GET("listing")
    suspend fun getAllProducts(): List<Listing>

    @POST
    suspend fun addSeller(): Nothing

    @PUT
    suspend fun updateSeller(): Nothing

    @DELETE
    suspend fun deleteSeller(): Nothing
}
