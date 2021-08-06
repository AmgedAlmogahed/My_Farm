package com.example.android.tasheel.data.repository

//import com.example.android.tasheel.data.UserPreferences
import com.example.farmer.data.network.AuthApi
import com.example.farmer.data.room.dao.AccountDao
import com.example.farmer.data.room.entities.Account
import com.example.farmer.data.room.entities.Farmers



class AuthRepository(private val accountDao: AccountDao? = null) {




//    suspend fun validateUser(
//        phoneNumber: String,
//        name: String
//    ) = AuthApi.RETROFIT_SERVICE.validateUser(name, phoneNumber)


    suspend fun addFarmer(farmer: Farmers) = accountDao?.insert(farmer)

    suspend fun addAccount(account: Account) = accountDao?.insert(account)

    fun getAccount() = accountDao?.getAllAccounts()


     suspend fun validateFarmer(phoneNumber: String) = accountDao?.validateFarmer(phoneNumber)

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