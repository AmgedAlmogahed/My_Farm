package com.example.farmer.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.data.network.responses.AccountResponse
import com.example.farmer.data.network.responses.LoginResponse
import com.example.farmer.data.room.entities.Account
import com.example.farmer.util.ApiStatus
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch


class AuthViewModel(val repository: AuthRepository, application: Application) :
    AndroidViewModel(application) {


    // Two-way databinding, exposing MutableLiveData
    val userName = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val pincode = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val whatsAppNumber = MutableLiveData<String>()
    val address = MutableLiveData<String>()

    val signInPhoneNumber = MutableLiveData<String>()

    val accounts = repository.getAccount()


    val type = MutableLiveData<String>()

    val codeOTP = MutableLiveData<String>()

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _warningFocus = MutableLiveData<String>()
    val warningFocus: LiveData<String>
        get() = _warningFocus

    private val _warningType = MutableLiveData<String>()
    val warningType: LiveData<String>
        get() = _warningType

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

//    val registerResponse: LiveData<List<Account?>>
//        get() = _registerResponse

//    val account = repository.getAccount()

    val resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()


    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _account = MutableLiveData<com.example.farmer.data.network.responses.Account>()
    val account : LiveData<com.example.farmer.data.network.responses.Account>
        get() = _account

    /**
     * When the property is clicked, set the [_navigateToLogin] [MutableLiveData]
     */
    fun Login() {
        _navigateToLogin.value = true
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedItem is set to null
     */
    fun displayLoginLayoutComplete() {
        _navigateToLogin.value = null
    }


    fun registerUserIfOTPTrue(
        userName: String?,
        phoneNumber: String,
        WhatsAppNumber: String?,
        city: String?,
        pincode: String?,
        address: String?,
        type: String?
    ) {

        viewModelScope.launch {
            val add = repository.addAccount(
                userName,
                phoneNumber,
                WhatsAppNumber,
                city,
                pincode,
                address,
                type
            )

            _account.value = add.account[0]
        }

    }

    fun signInIfOTPTrue(
        phoneNumber: String,
        whatsNumber: String?,
        accountId: Int?,
        name: String?,
        state: String?,
        pincode: String?,
        address: String?,
        type: String?
    ) {

        viewModelScope.launch {
            val account = Account(
                phoneNumber,
                whatsNumber,
                accountId,
                name,
                state,
                pincode,
                address,
                type
            )
            repository.addAccount(account)
        }

    }

    fun validateInputs() {

        //check if and validate whether the editTexts match the requirements or not if they match the validation method will be running to to check if they exist in the database
        if (checkIfRegistrationFormIsNUll(
                userName.value.toString().trim(),
                phoneNumber.value.toString().trim(),
                whatsAppNumber.value.toString().trim(),
                city.value.toString().trim(),
                pincode.value.toString().trim(),
                address.value.toString().trim()
            )
        ) return


        viewModelScope.launch {


            try {
                val phoneNumber = phoneNumber.value.toString().trim()

                val farmer = repository.validateAccount(phoneNumber)
                //validation     check whether customer registered or not for sign up
                if (farmer.error) {
                    _status.value = ApiStatus.DONE
                } else if (farmer.account[0].phone_number == phoneNumber) {
                    _toastMessage.value = "PhoneNumber already exist"
                }

            } catch (e: Exception) {
                Log.i("Authentication", e.toString())
                _toastMessage.value = "Check your Internet connection and try again"
            }

        }


    }

    fun validatePhoneNumberInput() {

        //check if and validate whether the editTexts match the requirements or not if they match the validation method will be running to to check if they exist in the database
        if (checkIfSignInFormIsNUll(
                signInPhoneNumber.value.toString().trim()
            )
        ) return

        viewModelScope.launch {


            try {
                val phoneNumber = signInPhoneNumber.value.toString().trim()


                val farmer = repository.validateAccount(phoneNumber)
                if (farmer.error) {
                    _toastMessage.value = "Phone number is not registered"
                } else if (farmer.account[0].phone_number == phoneNumber) {
                    _status.value = ApiStatus.DONE
                    _account.value = farmer.account[0]
                }
            } catch (e: Exception) {
                Log.i("Authentication", e.toString())
                _toastMessage.value = "Check Your Internet Connection and try again"
            }


        }

    }

    //check inputs nullability and corrections
    private fun checkIfSignInFormIsNUll(
        PhoneNumber: String?,

        ): Boolean {

        return when {
            PhoneNumber == "null" || PhoneNumber!!.isEmpty() -> {
                _warningType.value = "PHONE"
                _warningFocus.value = "PhoneNumber can't be empty"

                true
            }
            PhoneNumber.length < 10 -> {
                _warningType.value = "PHONE"
                _warningFocus.value = "PhoneNumber can't be less than 10 digits"
                true
            }

            else -> false
        }
    }


    //check inputs nullability and corrections
    private fun checkIfRegistrationFormIsNUll(
        Name: String?,
        PhoneNumber: String?,
        WhatsAppNumber: String?,
        City: String?,
        Pincode: String?,
        Address: String?
    ): Boolean {

        when {
            Name == "null" || Name!!.isEmpty() -> {
                _warningType.value = "NAME"
                _warningFocus.value = "Name can't be empty"
                return true
            }
            PhoneNumber == "null" || PhoneNumber!!.isEmpty() -> {
                _warningType.value = "PHONE"
                _warningFocus.value = "PhoneNumber can't be empty"

                return true
            }
            PhoneNumber.length < 10 -> {
                _warningType.value = "PHONE"
                _warningFocus.value = "PhoneNumber can't be less than 10 digits"
                return true
            }
            WhatsAppNumber == "null" || WhatsAppNumber!!.isEmpty() -> {
                _warningType.value = "WHATS"
                _warningFocus.value = "WhatsApp number can't be empty"

                return true
            }
            WhatsAppNumber.length < 9 -> {
                _warningType.value = "WHATS"
                _warningFocus.value = "WhatsApp number cant be less than 10 digits"
                return true
            }
            City == "null" || City!!.isEmpty() -> {
                _warningType.value = "CITY"
                _warningFocus.value = "City can't be empty"
                return true
            }
            Pincode == "null" || Pincode!!.isEmpty() -> {
                _warningType.value = "PINCODE"
                _warningFocus.value = "Pincode can't be empty"
                return true
            }
            Address == "null" || Address!!.isEmpty() -> {
                _warningType.value = "ADDRESS"
                _warningFocus.value = "Address can't be empty"
                return true
            }

            else -> return false
        }
    }


    fun endWarning() {
        _warningFocus.value = null
        _warningType.value = null
    }

    fun endToast() {
        _toastMessage.value = null
    }
}




