package com.example.farmer.ui.feedback

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.ui.farmer.FarmerViewModel

class FeedbackViewModelFactory(
    private val repository: Repository,
    private val id: Int,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            return FeedbackViewModel(repository, id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
