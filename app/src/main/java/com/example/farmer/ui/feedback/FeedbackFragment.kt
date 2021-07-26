package com.example.farmer.ui.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.farmer.R
import com.example.farmer.databinding.FragmentFeedbackBinding
import com.example.farmer.util.toolbar

class FeedbackFragment : Fragment() {

    private val args : FeedbackFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedbackBinding.inflate(inflater)

        toolbar(binding.toolbar7, R.id.fragment_customer)

        binding.farmerName.text = args.farmer
        binding.productName.text = args.farmer
        binding.state.text = args.state
        binding.district.text = args.district
        binding.quality.text = args.quality
        binding.stock.text = args.stock
        binding.price.text = args.price
        binding.unit.text = args.unit


        return binding.root
    }


}