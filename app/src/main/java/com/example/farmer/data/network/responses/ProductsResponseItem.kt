package com.example.farmer.data.network.responses
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductsResponseItem(
    val farmer_id: Int,
    val id: Int,
    val price: String?,
    val quality: String?,
    val status: String?,
    val stock: String?,
    val title: String?,
    val unit: String?,
) : Parcelable