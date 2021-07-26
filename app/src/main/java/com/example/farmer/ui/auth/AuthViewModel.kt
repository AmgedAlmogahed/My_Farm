package com.example.farmer.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.tasheel.data.repository.AuthRepository
import com.example.farmer.data.responses.LoginResponse
import com.example.farmer.data.room.entities.Account
import com.example.farmer.data.room.entities.Farmers
import com.example.farmer.util.ApiStatus
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch


class AuthViewModel(val repository: AuthRepository, application: Application) :
    AndroidViewModel(application) {


    // Two-way databinding, exposing MutableLiveData
    val userName = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val district = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val whatsAppNumber = MutableLiveData<String>()

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
        state: String?,
        district: String?
    ) {

        viewModelScope.launch {
            val account = Farmers(
                userName,
                phoneNumber,
                WhatsAppNumber,
                state,
                district
            )
            repository.addFarmer(account)
        }

    }

    fun signInIfOTPTrue(
        phoneNumber: String,
        type: String
    ) {

        viewModelScope.launch {
            val account = Account(
                phoneNumber,
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
                district.value.toString().trim(),
                state.value.toString().trim()
            )
        ) return


        viewModelScope.launch {

            val phoneNumber = phoneNumber.value.toString().trim()

            val farmer = repository.validateFarmer(phoneNumber)
            //validation     check whether customer registered or not for sign up
            if (farmer?.phoneNumber.equals(phoneNumber)) {
                _toastMessage.value = "PhoneNumber already exist"
            } else {
                _status.value = ApiStatus.DONE
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

            val phoneNumber = signInPhoneNumber.value.toString().trim()

            val farmer = repository.validateFarmer(phoneNumber)
            //validation     check whether customer registered or not for sign in
            if (farmer?.phoneNumber.equals(phoneNumber)) {
                _status.value = ApiStatus.DONE
            } else {
                _toastMessage.value = "Phone number is not registered"
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
        District: String?,
        State: String?
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
            District == "null" || District!!.isEmpty() -> {
                _warningType.value = "DISTRICT"
                _warningFocus.value = "District can't be empty"
                return true
            }
            State == "null" || State!!.isEmpty() -> {
                _warningType.value = "STATE"
                _warningFocus.value = "State can't be empty"
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




