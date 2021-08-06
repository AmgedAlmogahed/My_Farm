package com.example.farmer.ui.farmer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.data.network.responses.ProductsResponseItem
import com.example.farmer.data.room.entities.Products
import com.example.farmer.util.AVAILABLE
import com.example.farmer.util.ApiStatus
import kotlinx.coroutines.launch

class FarmerViewModel(val repository: Repository, application: Application) :
    AndroidViewModel(application) {


    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> get() = _snackBarText

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _farmerProductsResponse: MutableLiveData<List<ProductsResponseItem>> =
        MutableLiveData()
    val farmerProductsResponse: LiveData<List<ProductsResponseItem>>
        get() = _farmerProductsResponse

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


    init {
        getAllProducts()
    }
     fun getAllProducts() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val account = repository.getAccount()
                _farmerProductsResponse.value = repository.getFarmerProducts(account?.accountId!!)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("network error", e.toString())
                _status.value = ApiStatus.ERROR
                _farmerProductsResponse.value = ArrayList()
            }
        }
    }




    fun SignOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

}