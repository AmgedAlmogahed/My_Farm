package com.example.android.tasheel.data.repository

//import com.example.android.tasheel.data.UserPreferences
import com.example.farmer.data.network.AuthApi
import com.example.farmer.data.room.dao.FarmerDao
import com.example.farmer.data.room.entities.Account
import com.example.farmer.data.room.entities.Farmers



class AuthRepository(private val farmerDao: FarmerDao? = null) {




//    suspend fun validateUser(
//        phoneNumber: String,
//        name: String
//    ) = AuthApi.RETROFIT_SERVICE.validateUser(name, phoneNumber)


    suspend fun addFarmer(farmer: Farmers) = farmerDao?.insert(farmer)

    suspend fun addAccount(account: Account) = farmerDao?.insert(account)

    fun getAccount() = farmerDao?.getAllAccounts()


     suspend fun validateFarmer(phoneNumber: String) = farmerDao?.validateFarmer(phoneNumber)

    //network layer function

    /**
     * this functions accept 7 parameters for the details of a farmer and return a message weather the farmer is registered successfully or not
     */
    suspend fun addAccount(
        name: String?,
        phoneNumber: String?,
        whatsAppNumber: String?,
        city: String?,
        pincode: String?,
        address: String?,
        type: String?
    ) = AuthApi.RETROFIT_SERVICE.createAccount(
        name,
        phoneNumber,
        whatsAppNumber,
        city,
        pincode,
        address,
        type
    )

    suspend fun validateAccount(phoneNumber: String) =
        AuthApi.RETROFIT_SERVICE.validateAccount(phoneNumber)

    suspend fun getAccount(id: Int) =
        AuthApi.RETROFIT_SERVICE.getAccount(id)

    suspend fun getAccounts(type: String) =
        AuthApi.RETROFIT_SERVICE.getAccounts(type)


}