package com.example.farmer.data.room.entities

import androidx.room.*

@Entity(
    tableName = "products",foreignKeys = [
    ForeignKey(
        entity = Farmers::class,
        parentColumns = ["id"],
        childColumns = ["farmer_id"]

    )], indices = [Index(value = ["farmer_id"])]
)
data class Products(
    var title: String?,
    var price: String?,
    var quality: String?,
    var stock: String?,
    var unit: String?,
    @ColumnInfo(name = "farmer_id")
    var farmerId: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")
    var updateAt: Long? = null,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var productId: Int = 0
)
