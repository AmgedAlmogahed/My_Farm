package com.example.farmer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.farmer.util.CUSTOMER
import com.example.farmer.util.FARMER

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EntryFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_customer).setOnClickListener {
            findNavController().navigate(EntryFragmentDirections.actionFirstFragmentToSignIn(CUSTOMER))
        }

        view.findViewById<Button>(R.id.button_farmer).setOnClickListener {
            findNavController().navigate(EntryFragmentDirections.actionFirstFragmentToSignIn(FARMER))
        }


    }
}