package com.example.farmer.ui.customer

import android.content.Intent
import android.net.Uri
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
import com.example.farmer.databinding.FragmentCustomerProductsBinding

import com.example.farmer.ui.auth.AuthActivity
import com.example.farmer.util.startNewActivity
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CustomerProductsFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCustomerProductsBinding.inflate(inflater)

        toolbar(binding.toolbar, R.id.fragment_customer)

        binding.toolbar.inflateMenu(R.menu.menu_main)



        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).productsDao

        val dataSource = Repository(dao)

        val viewModelFactory = CustomerProductsViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(CustomerProductsViewModel::class.java)

        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewmodel = viewModel

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    viewModel.SignOut()
                    requireActivity().startNewActivity(AuthActivity::class.java)
                    true
                }
                else -> false
            }
        }

        val adapter = ProductsAdapter(
            ProductsAdapter.OnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:${it.phone_number}")
                startActivity(dialIntent)
//                Toast.makeText(application, "call ${it.farmer_Phone_Number}", Toast.LENGTH_SHORT).show()
            },
            ProductsAdapter.OnClickListener {
                val whats = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=" + "+917304049853" + "&text=" + "Hello")
                )
                startActivity(whats)
            },
            ProductsAdapter.OnClickListener{
                findNavController().navigate(CustomerProductsFragmentDirections.actionCustomerProductsToFeedback(
                    it.title,
                    it.farmer_name,
                    it.state,
                    it.district,
                    it.price,
                    it.quality,
                    it.stock,
                    it.unit
                ))
            }
        )


        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.listing.adapter = adapter

        viewModel.products?.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.apply {
                        adapter.submitList(it)
                        listing.visible(true)
                        statusText.visible(false)
//                        statusImage.visible(false)
                    }
                } else {
                    binding.apply {
                        listing.visible(false)
                        statusText.visible(true)
//                        statusImage.visible(true)
                    }
                }
            }
        })
//        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
//        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
//        // for another navigation event.
//        viewModel.navigateToSelectedRestaurant.observe(requireActivity(), Observer {
//            if (null != it) {
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(RestaurantsFragmentDirections.actionRestaurantsFragmentsToMenuFragment(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                viewModel.displayRestaurantDetailsComplete()
//            }
//        })


//        apiStatus(viewModel, binding.statusImage, binding.statusText, binding.progressBar)


        return binding.root

    }


}