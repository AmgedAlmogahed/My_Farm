package com.example.farmer.data.network.responses
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AllProductsResponse(
    val address: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val pincode: String,
    val price: String,
    val quality: String,
    val state: String,
    val stock: String,
    val title: String,
    val unit: String,
    val whatsapp_number: String
) : Parcelable