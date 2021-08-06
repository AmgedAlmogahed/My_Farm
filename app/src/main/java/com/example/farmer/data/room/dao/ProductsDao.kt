package com.example.farmer.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.farmer.data.room.entities.Account
import com.example.farmer.data.room.entities.CustomerProducts
import com.example.farmer.data.room.entities.Farmers
import com.example.farmer.data.room.entities.Products

@Dao
interface ProductsDao {
    @Insert
    suspend fun insert(product: Products)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param product new value to write
     */
    @Update
    suspend fun update(product: Products)



    @Query("SELECT * FROM accounts ORDER BY id DESC")
    fun getAllAccounts(): List<Account>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM products")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM products WHERE farmer_id = :key ORDER BY id DESC")
    fun getAllProducts(key: Long): LiveData<List<Products>>

    @Query("SELECT * FROM accounts ORDER BY id DESC LIMIT 1")
    suspend fun getToAccount(): Account?
    /**
     * Selects and returns the latest product.
     */
    @Query("SELECT * FROM products ORDER BY id DESC LIMIT 1")
    suspend fun getToService(): Products?

    /**
     * Selects and returns the product with given productId.
     */
    @Query("SELECT * from products WHERE Id = :key")
    fun getServiceWithId(key: Long): LiveData<Products>

    @Transaction
    @Query("SELECT farmers.farmer_name, farmers.phone_number, farmers.whats_app_number, farmers.state, farmers.district, products.title ,products.price, products.quality, products.stock,products.unit,products.farmer_id, products.id FROM farmers,products WHERE farmers.id = farmer_id ")
    fun getAllProducts(): LiveData<List<CustomerProducts?>>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM accounts")
    suspend fun clearAccounts()

    @Query("SELECT * from accounts WHERE id = :key")
    suspend fun getAccount(key: Long): Account?

    /**
     * Selects and returns the rent with given rentId.
     */
    @Query("SELECT * FROM accounts LIMIT 1")
    suspend fun validateFarmer(): Account?

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from accounts WHERE phone_number = :key")
    suspend fun get(key: String): Account?


}