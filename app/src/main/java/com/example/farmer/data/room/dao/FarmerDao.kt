package com.example.farmer.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.farmer.data.room.entities.Account
import com.example.farmer.data.room.entities.Farmers

@Dao
interface FarmerDao {

    @Insert
    suspend fun insert(service: Farmers)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param farmer new value to write
     */
    @Update
    suspend fun update(farmer: Farmers)



    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM farmers")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM farmers ORDER BY id DESC")
    fun getAllFarmers(): LiveData<List<Farmers>>

    /**
     * Selects and returns the latest farmer.
     */
    @Query("SELECT * FROM farmers ORDER BY id DESC LIMIT 1")
    suspend fun getToFarmer(): Farmers?

    /**
     * Selects and returns the farmer with given farmerId.
     */
    @Query("SELECT * from farmers WHERE Id = :key")
    fun getFarmerWithId(key: Long): LiveData<Farmers>


    @Insert
    suspend fun insert(account: Account)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param farmer new value to write
     */
    @Update
    suspend fun update(account: Account)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from accounts WHERE id = :key")
    suspend fun getAccount(key: Long): Account?



    @Query("SELECT * from farmers WHERE phone_number = :key")
    suspend fun validateFarmer(key: String) : Account?

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM accounts ORDER BY id DESC")
    fun getAllAccounts(): LiveData<List<Account>>


    /**
     * Selects and returns the farmer with given farmerId.
     */
    @Query("SELECT * from accounts WHERE Id = :key")
    fun getAccountWithId(key: Long): LiveData<Account>
}