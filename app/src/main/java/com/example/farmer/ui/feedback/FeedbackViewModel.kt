package com.example.farmer.ui.feedback

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.data.network.responses.FeedbackResponse
import com.example.farmer.data.room.entities.Products
import com.example.farmer.util.ApiStatus
import kotlinx.coroutines.launch

class FeedbackViewModel(val repository: Repository, val id: Int, application: Application) :
    AndroidViewModel(application) {


    // Two-way databinding, exposing MutableLiveData
    val feedback = MutableLiveData<String>()

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> get() = _snackBarText

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _feedbacks: MutableLiveData<List<FeedbackResponse>> =
        MutableLiveData()
    val feedbacks: LiveData<List<FeedbackResponse>>
        get() = _feedbacks

    /**
     * Variable that tells the fragment whether it should navigate to [ProductsFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToAddFeedback = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [Products]
     */
    val navigateToAddFeedback: LiveData<Boolean?>
        get() = _navigateToAddFeedback


    init {
        getAllProducts(id)
    }

    fun getAllProducts(id: Int) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val account = repository.getAccount()
                _feedbacks.value = repository.getFeedback(id)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.i("network error", e.toString())
                _status.value = ApiStatus.ERROR
                _feedbacks.value = ArrayList()
            }
        }
    }

    fun addFeedbackToDatabase() {

        val currentFeedback = feedback.value

        if (checkIfTextNull(
                currentFeedback,
            )
        ) return

        viewModelScope.launch {
            val account = repository.getPhoneNumber()
            val customer = repository.getId(account?.phoneNumber.toString())



            repository.addFeedback(
                id,
                customer?.accountId,
                feedback.value,
                customer?.name
            )

            _navigateToAddFeedback.value = true
            _snackBarText.value = "Product added successfully"
        }

    }

    private fun checkIfTextNull(
        feedback: String?,
    ): Boolean {
        return when {
            feedback == null || feedback.isEmpty() -> {
                _snackBarText.value = "Feedback can't be empty"
                true
            }
            else -> false
        }

    }

    /**
     * Call this immediately after navigating to [ProductsFragment]
     */
    fun doneNavigating() {
        _navigateToAddFeedback.value = null
    }

    fun setSnackEmpty() {
        _snackBarText.value = null
    }


}