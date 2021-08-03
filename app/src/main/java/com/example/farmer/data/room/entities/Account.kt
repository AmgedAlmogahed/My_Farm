package com.example.farmer.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
    @ColumnInfo(name = "whats_number")
    val whatsNumber: String?,
    @ColumnInfo(name = "account_id")
    val accountId:Int?,
    val name: String?,
    val state: String?,
    val pincode: String?,
    val address: String?,
    val type: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val Id: Int? = 0
    )