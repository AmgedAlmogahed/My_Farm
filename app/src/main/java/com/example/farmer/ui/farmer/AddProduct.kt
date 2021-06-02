package com.example.farmer.ui.farmer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.farmer.R
import com.example.farmer.data.repository.Repository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentAddProductBinding
import com.example.farmer.databinding.FragmentFarmerProductsBinding
import com.example.farmer.util.toolbar
import com.google.android.material.snackbar.Snackbar


class AddProduct : Fragment() {
    lateinit var viewModel: FarmerViewModel
    lateinit var binding: FragmentAddProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater)

        toolbar(binding.toolbar4,R.id.fragment2)

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).productsDao

        val dataSource = Repository(dao)

        val viewModelFactory = FarmerViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(FarmerViewModel::class.java)

        binding.data = viewModel

        binding.lifecycleOwner = this


        viewModel.snackBarText.observe(viewLifecycleOwner, Observer { text ->
            if (text != null) {
                Snackbar.make(binding.addProductLayout, text, Snackbar.LENGTH_LONG).show()
                viewModel.setSnackEmpty()
            }
        })

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        viewModel.navigateToProducts.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                    findNavController().navigate(R.id.action_addProduct_to_farmerProducts)

//                 Reset state to make sure we only navigate once, even if the device
//                 has a configuration change.
                viewModel.doneNavigating()
            }
        })


        return binding.root
    }
}
