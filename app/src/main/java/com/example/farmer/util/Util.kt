package com.example.farmer.util

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.farmer.R
import com.example.farmer.ui.customer.CustomerProductsViewModel
import com.example.farmer.ui.farmer.FarmerViewModel
import com.example.farmer.ui.feedback.FeedbackViewModel
import com.google.android.material.snackbar.Snackbar

enum class ApiStatus { LOADING, ERROR, DONE }

const val CUSTOMER = "customer"
const val FARMER = "farmer"
const val AVAILABLE = "available"
const val UNAVAILABLE = "unavailable"


fun toast(message: String?) {
    Toast.makeText(MyApplication.instance, message, Toast.LENGTH_LONG).show()
}

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startNewActivity(activity: Class<A>, type: String?) {
    Intent(this, activity).also {
        it.putExtra("type", type)
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}


fun Fragment.toolbar(toolbar: Toolbar, nav: Int) {
    toolbar.apply {
        this.layoutDirection = View.LAYOUT_DIRECTION_RTL
        this.setTitleTextColor(resources.getColor(R.color.white))
    }

    val navController = Navigation.findNavController(requireActivity(), nav)
    val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
    NavigationUI.setupWithNavController(
        toolbar, navController, appBarConfiguration
    )
}

fun Fragment.apiStatus(
    viewModel: FarmerViewModel,
    statusImage: ImageView,
    statusText: TextView,
    progressBar: ProgressBar
) {
    viewModel.status.observe(viewLifecycleOwner, Observer { status ->
        status?.let {
            when (status) {
                ApiStatus.LOADING -> {
                    progressBar.visible(true)
                    statusText.text = "Loading"
                }
                ApiStatus.ERROR -> {
                    statusImage.visible(true)
                    progressBar.visible(false)
                    statusImage.setImageResource(R.drawable.ic_connection_error)
                    statusText.text = "Check Your Internet Connection"

                }
                ApiStatus.DONE -> {
                    statusImage.visible(false)
                    statusText.visible(false)
                    progressBar.visible(false)
                }
            }
        }
    })
}

fun Fragment.apiStatus(
    viewModel: FeedbackViewModel,
    statusImage: ImageView,
    statusText: TextView,
    progressBar: ProgressBar
) {
    viewModel.status.observe(viewLifecycleOwner, Observer { status ->
        status?.let {
            when (status) {
                ApiStatus.LOADING -> {
                    progressBar.visible(true)
                    statusText.text = "Loading"
                }
                ApiStatus.ERROR -> {
                    statusImage.visible(true)
                    progressBar.visible(false)
                    statusImage.setImageResource(R.drawable.ic_connection_error)
                    statusText.text = "Check Your Internet Connection"

                }
                ApiStatus.DONE -> {
                    statusImage.visible(false)
                    statusText.visible(false)
                    progressBar.visible(false)
                }
            }
        }
    })
}

fun Fragment.apiStatus(
    viewModel: CustomerProductsViewModel,
    statusImage: ImageView,
    statusText: TextView,
    progressBar: ProgressBar
) {
    viewModel.status.observe(viewLifecycleOwner, Observer { status ->
        status?.let {
            when (status) {
                ApiStatus.LOADING -> {
                    progressBar.visible(true)
                    statusText.text = "Loading"
                }
                ApiStatus.ERROR -> {
                    statusImage.visible(true)
                    progressBar.visible(false)
                    statusImage.setImageResource(R.drawable.ic_connection_error)
                    statusText.text = "Check Your Internet Connection"

                }
                ApiStatus.DONE -> {
                    statusImage.visible(false)
                    statusText.visible(false)
                    progressBar.visible(false)
                }
            }
        }
    })
}