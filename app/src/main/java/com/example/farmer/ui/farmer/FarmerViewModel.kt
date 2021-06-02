package com.example.farmer.ui.farmer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.farmer.data.repository.Repository
import com.example.farmer.data.room.entities.Products
import kotlinx.coroutines.launch

class FarmerViewModel(val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    private val key: Long = 1

    val products = repository.getFarmerProducts(key)

    // Two-way databinding, exposing MutableLiveData
    val name = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val price = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val quality = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val stock = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val unit = MutableLiveData<String>()

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> get() = _snackBarText


    /**
     * Variable that tells the fragment whether it should navigate to [ProductsFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToProducts = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [Products]
     */
    val navigateToProducts: LiveData<Boolean?>
        get() = _navigateToProducts


    /**
     * Call this immediately after navigating to [ProductsFragment]
     */
    fun doneNavigating() {
        _navigateToProducts.value = null
    }

    fun setSnackEmpty() {
        _snackBarText.value = null
    }

    fun addProductToTheDatabase() {


        val currentName = name.value
        val currentPrice = price.value
        val currentQuality = quality.value
        val currentStock = stock.value
        val currentUnit = unit.value


        if (checkIfTextNull(
                currentName,
                currentPrice,
                currentQuality,
                currentStock,
                currentUnit,

            )
        ) return

        viewModelScope.launch {
            val account = repository.getPhoneNumber()
            val farmer = repository.getId(account?.phoneNumber.toString())


        val product = Products(
            currentName,
            currentPrice,
            currentQuality,
            currentStock,
            currentUnit,
            farmer?.farmerId!!
        )


            repository.addProduct(product)

            _navigateToProducts.value = true
            _snackBarText.value = "Product added successfully"
        }

    }

    private fun checkIfTextNull(
        currentName: String?,
        currentPrice: String?,
        currentQuality: String?,
        currentStock: String?,
        currentUnit: String?,
    ): Boolean {
        when {
            currentName == null || currentName.isEmpty() -> {
                _snackBarText.value = "Name Can't be empty"
                return true
            }
            currentQuality == null || currentQuality.isEmpty() -> {
                _snackBarText.value = "Quality Can't be empty"
                return true
            }
            currentPrice == null || currentPrice.isEmpty() -> {
                _snackBarText.value = "Price Can't be empty"
                return true
            }
            currentStock == null || currentStock.isEmpty() -> {
                _snackBarText.value = "Stock Can't be empty"
                return true
            }
            currentUnit == null || currentUnit.isEmpty() -> {
                _snackBarText.value = "Unit Can't be empty"
                return true
            }
            else -> return false
        }

    }

    fun SignOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

}