package com.example.farmer.ui.farmer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.farmer.R
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentFarmerProductsBinding
import com.example.farmer.ui.auth.AuthActivity
import com.example.farmer.util.startNewActivity
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible


class FarmerProductsFragment : Fragment() {

    lateinit var viewModel: FarmerViewModel
    lateinit var binding: FragmentFarmerProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFarmerProductsBinding.inflate(inflater)


        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).productsDao

        val dataSource = Repository(dao)

        val viewModelFactory = FarmerViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(FarmerViewModel::class.java)


        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_farmerProducts_to_addProduct)
        }

        val adapter = FarmerProductsAdapter()

        binding.recyclerViewFarmerList.adapter = adapter

        viewModel.farmerProductsResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.apply {
                        adapter.submitList(it)
                        recyclerViewFarmerList.visible(true)
                        textView20.visible(false)
                    }
                } else {
                    binding.apply {
                        recyclerViewFarmerList.visible(false)
                        textView20.visible(true)
                    }
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar(binding.toolbar3, R.id.fragment_farmer)
        binding.toolbar3.inflateMenu(R.menu.menu_main)

        binding.toolbar3.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    viewModel.SignOut()
                    requireActivity().startNewActivity(AuthActivity::class.java)
                    true
                }
                else -> false
            }
        }
    }

}
