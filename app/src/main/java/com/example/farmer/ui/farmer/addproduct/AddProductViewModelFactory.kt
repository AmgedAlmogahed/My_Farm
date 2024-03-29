package com.example.farmer.ui.farmer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.ui.farmer.addproduct.AddProductViewModel

class AddProductViewModelFactory(
    private val repository: Repository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            return AddProductViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}