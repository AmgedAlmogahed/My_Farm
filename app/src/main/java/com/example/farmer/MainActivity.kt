package com.example.farmer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.ui.auth.AuthActivity
import com.example.farmer.ui.auth.AuthViewModel
import com.example.farmer.ui.auth.AuthViewModelFactory
import com.example.farmer.ui.customer.CustomerActivity
import com.example.farmer.util.CUSTOMER
import com.example.farmer.util.startNewActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val dao = AppDatabase.getInstance(application).farmerDao

        val dataSource = AuthRepository(dao)

        val viewModelFactory = AuthViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AuthViewModel::class.java)



        viewModel.accounts?.observe(this, Observer {


            val activity = if (it.isEmpty()) AuthActivity::class.java else if (it[0].type == CUSTOMER) CustomerActivity::class.java else CustomerActivity::class.java
            startNewActivity(activity)
        })
    }

}