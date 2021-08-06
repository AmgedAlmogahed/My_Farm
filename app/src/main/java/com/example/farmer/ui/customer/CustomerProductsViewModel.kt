package com.example.farmer.ui.customer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.data.network.responses.AllProductsResponse
import com.example.farmer.data.room.entities.CustomerProducts
import com.example.farmer.util.ApiStatus
import kotlinx.coroutines.launch

class CustomerProductsViewModel(
    val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    private val _listingResponse: MutableLiveData<List<CustomerProducts?>> = MutableLiveData()
    val listingResponse: LiveData<List<CustomerProducts?>>
        get() = _listingResponse

    private val _products: MutableLiveData<List<AllProductsResponse>> = MutableLiveData()
    val products: LiveData<List<AllProductsResponse>>
        get() = _products

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedRestaurant = MutableLiveData<CustomerProducts>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedRestaurant: LiveData<CustomerProducts>
        get() = _navigateToSelectedRestaurant


    init {
        getAllSellers()
    }

    private fun getAllSellers() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _products.value = repository.getAllProducts()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("network error", e.toString())
                _status.value = ApiStatus.ERROR
                _products.value = ArrayList()
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedRestaurant] [MutableLiveData]
     */
    fun displayRestaurantDetails(listing: CustomerProducts) {
        _navigateToSelectedRestaurant.value = listing
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedRestaurant is set to null
     */
    fun displayRestaurantDetailsComplete() {
        _navigateToSelectedRestaurant.value = null
    }

    fun SignOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

}