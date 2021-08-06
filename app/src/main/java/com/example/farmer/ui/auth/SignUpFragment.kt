package com.example.farmer.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.R
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentSignUpBinding
import com.example.farmer.util.ApiStatus
import com.example.farmer.util.toast
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible


class SignUpFragment : Fragment() {

    lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentSignUpBinding
    val args: SignUpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)

        toolbar(binding.toolbar2, R.id.fragment)

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).accountDao

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
                        findNavController().navigate(
                            SignUpFragmentDirections.actionSignUpToOtp(
                                viewModel.userName.value.toString().trim(),
                                viewModel.phoneNumber.value.toString().trim(),
                                viewModel.whatsAppNumber.value.toString().trim(),
                                viewModel.city.value.toString().trim(),
                                viewModel.pincode.value.toString().trim(),
                                viewModel.address.value.toString().trim(),
                                "0",
                                args.type
                            )
                        )
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
                    "CITY" -> {
                        binding.editTextCity.error = warning
                        binding.editTextCity.requestFocus()
                        return@let
                    }
                    "PINCODE" -> {
                        binding.editTextPincode.error = warning
                        binding.editTextPincode.requestFocus()
                        return@let
                    }
                    "ADDRESS" -> {
                        binding.editTextAddress.error = warning
                        binding.editTextAddress.requestFocus()
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