package com.example.farmer.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.HomeActivity
import com.example.farmer.R
import com.example.farmer.data.room.AppDatabase
import com.example.farmer.databinding.FragmentOtpBinding
import com.example.farmer.util.startNewActivity
import com.example.farmer.util.toast
import com.example.farmer.util.toolbar
import com.example.farmer.util.visible

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class Otp : Fragment() {

    lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentOtpBinding
    private lateinit var phoneAuthCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var fAuth: FirebaseAuth
    private var state: String? = null
    private var district: String? = null
    private var name: String? = null
    private var whatsAppNumber: String? = null
    private lateinit var i: String
    private lateinit var phoneNumber: String
    private var verificationId: String = ""
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(inflater)

        toolbar(binding.toolbar6, R.id.fragment)

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).farmerDao

        val dataSource = AuthRepository(dao)

        val viewModelFactory = AuthViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AuthViewModel::class.java)

        val args = OtpArgs.fromBundle(requireArguments())
        name = args.userName
        phoneNumber = args.phoneNumber
        whatsAppNumber = args.WhatsNumber
        state = args.state
        district = args.district
        i = args.i


        fAuth = FirebaseAuth.getInstance()

        phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Log.d("Firebase :", "onVerificationCompleted:$phoneAuthCredential")
                addPhoneNumber(phoneAuthCredential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Toast.makeText(application, exception.message!!, Toast.LENGTH_SHORT).show()
                Log.v("Firebase : ", exception.message!!)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.d("Firebase :", "onCodeSent:$verificationId")
                this@Otp.verificationId = verificationId
                viewModel.resendToken.value = token
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                binding.resendOtp.isEnabled = true
            }
        }


        firebaseInstance(phoneNumber)
        // observe EditTexts and show corrections
        viewModel.warningFocus.observe(viewLifecycleOwner, Observer { warning ->
            warning?.let {
                when (viewModel.warningType.value) {
                    "CODE" -> {
                        binding.OtpEditText.error = warning
                        binding.OtpEditText.requestFocus()
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

        binding.resendOtp.setOnClickListener {
            binding.progressBar3.visible(true)
            resendVerificationCode("+91$phoneNumber", viewModel.resendToken.value)
        }

        //verify OTP
        binding.confirmOtp.setOnClickListener {
            val code = binding.OtpEditText.text.toString().trim()

            if (code.isEmpty()) {
                binding.OtpEditText.error = "Enter the OTP"
                binding.OtpEditText.requestFocus()
            } else {
                if (verificationId.isNullOrEmpty() || verificationId.isNullOrBlank() || verificationId == "")
                    toast("Wrong code please try again")
                verificationId.let {
                    val credential = PhoneAuthProvider.getCredential(verificationId, code)
                    addPhoneNumber(credential)
                }

            }
        }


        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            requireActivity().startNewActivity(HomeActivity::class.java)
        })
        return binding.root


    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())    // Activity (for callback binding)
            .setCallbacks(phoneAuthCallbacks)  // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())

        binding.progressBar3.visible(false)
        binding.resendOtp.isEnabled = false
    }

    private fun firebaseInstance(number: String) {
        val phoneNumber = "+91$number"

        val options = PhoneAuthOptions.newBuilder(fAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())    // Activity (for callback binding)
            .setCallbacks(phoneAuthCallbacks)  // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun addPhoneNumber(phoneAuthCredential: PhoneAuthCredential) {
        binding.progressBar3.visible(true)
        fAuth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Verification Successful")
                    binding.progressBar3.visible(false)
                    if (i == "0") {
                        viewModel.registerUserIfOTPTrue(
                            name,
                            phoneNumber,
                            whatsAppNumber,
                            state,
                            district
                        )
                        viewModel.signInIfOTPTrue(phoneNumber)
                    } else if (i == "1") {
                        viewModel.signInIfOTPTrue(phoneNumber)
                    }
                    viewModel.Login()
                } else {
                    binding.progressBar3.visible(false)
                    toast(task.exception?.message!!)
                    Log.v("Firebase : ", task.exception?.message!!)
                }
            }
    }
}
