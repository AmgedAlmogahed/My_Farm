package com.example.farmer

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.ui.auth.AuthActivity
import com.example.farmer.ui.auth.AuthViewModel
import com.example.farmer.ui.auth.AuthViewModelFactory
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


            val activity = if (it.isEmpty()) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })
    }

}