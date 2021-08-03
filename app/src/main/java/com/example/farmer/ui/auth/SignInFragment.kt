package com.example.farmer.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.R
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentSignInBinding
import com.example.farmer.util.ApiStatus
import com.example.farmer.util.toast
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible

class SignInFragment : Fragment() {
    lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentSignInBinding
    val args : SignInFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater)

        toolbar(binding.toolbar5,R.id.fragment)

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).farmerDao

        val dataSource = AuthRepository(dao)

        val viewModelFactory = AuthViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AuthViewModel::class.java)

        binding.lifecycleOwner = this
        // Giving the binding access to the AuthViewModel
        binding.viewmodel = viewModel

        binding.register.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInToSignUp(args.type))
        }


        //1 check registration api status and if true the progressbar will stop and move to the verification layout
        //2 send OTP before moving for next layout
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status.let {
                @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                when (status) {
                    ApiStatus.LOADING -> {
                        binding.progressBar5.visible(true)
                    }
                    ApiStatus.DONE -> {
                        binding.progressBar5.visible(false)
                        findNavController().navigate(SignInFragmentDirections.actionSignInToOtp(
                             viewModel.signInPhoneNumber.value.toString().trim(),
                            "1",
                            args.type,
                            ))
                    }
                    ApiStatus.ERROR -> {
                        binding.progressBar5.visible(false)

                    }
                }
            }

        })

        // observe EditTexts and show corrections
        viewModel.warningFocus.observe(viewLifecycleOwner, Observer { warning ->
            warning?.let {
                when (viewModel.warningType.value) {
                    "PHONE" -> {
                        binding.editTextPhoneNumber.error = warning
                        binding.editTextPhoneNumber.requestFocus()
                        return@let
                    }
                }
                viewModel.endWarning()
            }
        })


        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                toast(it)
                viewModel.endToast()
            }
        })

        return binding.root

    }



}