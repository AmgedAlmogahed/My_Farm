package com.example.android.tasheel.data.repository

//import com.example.android.tasheel.data.UserPreferences
import com.example.farmer.data.network.AuthApi
import com.example.farmer.data.room.dao.FarmerDao
import com.example.farmer.data.room.entities.Account
import com.example.farmer.data.room.entities.Farmers


class AuthRepository(private val farmerDao: FarmerDao? = null) {

    suspend fun login(
        phoneNumber: String,
        password: String
    ) = AuthApi.retrofitService.login(phoneNumber, password)

    suspend fun Register(
        name: String,
        phoneNumber: String,
        whatsAppNumber: String,
        state: String,
        district: String
    ) = AuthApi.retrofitService.createCustomerAccount(
        name,
        phoneNumber,
        whatsAppNumber,
        state,
        district
    )

    suspend fun validateUser(
        phoneNumber: String,
        name: String
    ) = AuthApi.retrofitService.validateUser(name, phoneNumber)


    suspend fun addFarmer(farmer: Farmers) = farmerDao?.insert(farmer)

    suspend fun addAccount(account: Account) = farmerDao?.insert(account)

    fun getAccount() = farmerDao?.getAllAccounts()


     suspend fun validateFarmer(phoneNumber: String) = farmerDao?.validateFarmer(phoneNumber)

}