package com.example.farmer.ui.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.farmer.R
import com.example.farmer.data.network.repository.Repository
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentFeedbackBinding
import com.example.farmer.util.apiStatus
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible

class FeedbackFragment : Fragment() {

    private val args: FeedbackFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedbackBinding.inflate(inflater)

        toolbar(binding.toolbar7, R.id.fragment_customer)

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).productsDao

        val dataSource = Repository(dao)

        val viewModelFactory = FeedbackViewModelFactory(dataSource, args.id, application)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(FeedbackViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.farmer.text = args.farmer
        binding.productName.text = args.title
        binding.state.text = args.state
        binding.address.text = args.address
        binding.quality.text = args.quality
        binding.stock.text = args.stock
        binding.price.text = args.price
        binding.unit.text = args.unit
        binding.pincode.text = args.pincode


        val adapter = FeedbackAdapter()


        viewModel.feedbacks.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.apply {
                        adapter.submitList(it)
                        feedbackList.visible(true)
                        statusText.visible(false)
                    }
                } else {
                    binding.apply {
                        feedbackList.visible(false)
                        statusText.visible(true)
                        statusText.text = "No feedback added yet"
                    }
                }
            }
        })

        binding.feedbackList.adapter = adapter

        apiStatus(viewModel, binding.statusImage, binding.statusText, binding.progressBar)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                FeedbackFragmentDirections.actionFeedbackToAddFeedback(
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
                )
            )
        }
        return binding.root


    }


}