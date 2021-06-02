package com.example.farmer.ui.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.tasheel.data.repository.AuthRepository

import com.example.farmer.ui.auth.AuthViewModel

class AuthViewModelFactory(
        private val repository: AuthRepository,
        private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return  AuthViewModel(repository, application)  as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
