package com.example.farmer.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.farmer.R
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentAddFeedbackBinding
import com.example.farmer.ui.feedback.AddFeedbackFragmentArgs
import com.example.farmer.ui.feedback.FeedbackViewModel
import com.example.farmer.ui.feedback.FeedbackViewModelFactory
import com.example.farmer.util.toolbar
import com.google.android.material.snackbar.Snackbar

class AddFeedbackFragment : Fragment() {
    lateinit var viewModel: FeedbackViewModel
    lateinit var binding: FragmentAddFeedbackBinding
    private val args : AddFeedbackFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFeedbackBinding.inflate(inflater)

        toolbar(binding.toolbar8, R.id.fragment_customer)

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).productsDao

        val dataSource = Repository(dao)

        val viewModelFactory = FeedbackViewModelFactory(dataSource, args.id, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(FeedbackViewModel::class.java)

        binding.data = viewModel

        binding.lifecycleOwner = this


        viewModel.snackBarText.observe(viewLifecycleOwner, Observer { text ->
            if (text != null) {
                Snackbar.make(binding.addFeedbackLayout, text, Snackbar.LENGTH_LONG).show()
                viewModel.setSnackEmpty()
            }
        })

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        viewModel.navigateToAddFeedback.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                findNavController().navigate(AddFeedbackFragmentDirections.actionAddFeedbackToFeedback(
                    args.title,
                    args.farmer,
                    args.state,
                    args.address,
                    args.price,
                    args.quality,
                    args.stock,
                    args.unit,
                    args.id,
                    args.pincode
                ))

//                 Reset state to make sure we only navigate once, even if the device
//                 has a configuration change.
                viewModel.doneNavigating()
            }
        })


        return binding.root
    }
}
