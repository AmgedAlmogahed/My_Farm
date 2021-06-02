package com.example.farmer.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.R
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentSignUpBinding
import com.example.farmer.util.ApiStatus
import com.example.farmer.util.toast
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible


class SignUp : Fragment() {

    lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)

        toolbar(binding.toolbar2, R.id.fragment)

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



        //1 check registration api status and if true the progressbar will stop and move to the verification layout
        //2 send OTP before moving for next layout
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status.let {
                @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                when (status) {
                    ApiStatus.LOADING -> {
                        binding.progressBar2.visible(true)
                    }
                    ApiStatus.DONE -> {
                        binding.progressBar2.visible(false)
                        findNavController().navigate(SignUpDirections.actionSignUpToOtp(
                            viewModel.userName.value.toString().trim(),
                            viewModel.phoneNumber.value.toString().trim(),
                            viewModel.whatsAppNumber.value.toString().trim(),
                            viewModel.state.value.toString().trim(),
                            viewModel.district.value.toString().trim(),
                            "0"
                        ))
                    }
                    ApiStatus.ERROR -> {
                        binding.progressBar2.visible(false)

                    }
                }
            }

        })

        // observe EditTexts and show corrections
        viewModel.warningFocus.observe(viewLifecycleOwner, Observer { warning ->
            warning?.let {
                when (viewModel.warningType.value) {
                    "NAME" -> {
                        binding.editTextFarmerName.error = warning
                        binding.editTextFarmerName.requestFocus()
                        return@let
                    }
                    "PHONE" -> {
                        binding.editTextPhoneNumber.error = warning
                        binding.editTextPhoneNumber.requestFocus()
                        return@let
                    }
                    "WHATS" -> {
                        binding.editTextWhatsAppNumber.error = warning
                        binding.editTextWhatsAppNumber.requestFocus()
                        return@let
                    }
                    "STATE" -> {
                        binding.editTextState.error = warning
                        binding.editTextState.requestFocus()
                        return@let
                    }
                    "DISTRICT" -> {
                        binding.editTextDistrict.error = warning
                        binding.editTextDistrict.requestFocus()
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