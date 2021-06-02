package com.example.farmer.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farmers")
data class Farmers(
    @ColumnInfo(name = "farmer_name")
    var name: String?,

    @ColumnInfo(name = "phone_number")
    var phoneNumber:String,

    @ColumnInfo(name = "whats_app_number")
    var whatsAppNumber:String?,

    var state:String?,

    var district:String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var farmerId: Int = 0
)
