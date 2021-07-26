package com.example.farmer.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
    val type: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val accountId: Int? = 0
    )