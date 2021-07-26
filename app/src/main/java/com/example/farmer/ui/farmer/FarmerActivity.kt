package com.example.farmer.ui.farmer

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.R
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.ui.auth.AuthViewModel
import com.example.farmer.ui.auth.AuthViewModelFactory


class FarmerActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer)
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        val dao = AppDatabase.getInstance(application).farmerDao

        val dataSource = AuthRepository(dao)

        val viewModelFactory = AuthViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AuthViewModel::class.java)

    }


}