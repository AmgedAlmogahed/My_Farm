package com.example.farmer

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.ui.auth.AuthActivity
import com.example.farmer.ui.auth.AuthViewModel
import com.example.farmer.ui.auth.AuthViewModelFactory
import com.example.farmer.ui.farmer.FarmerViewModel
import com.example.farmer.util.startNewActivity


class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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