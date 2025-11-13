package com.example.vendoapp.ui.login.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.auth.otp.VerifyOtpRequest
import com.example.vendoapp.domain.repository.AuthRepository
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _otpState = MutableStateFlow<Resource<Unit>>(Resource.Idle())
    val otpState: StateFlow<Resource<Unit>> = _otpState

    fun verifyOtp(email: String, code: String) {
        viewModelScope.launch {
            _otpState.value = Resource.Loading()
            val result = repository.verifyOtp(
                VerifyOtpRequest(target = email, channel = "EMAIL", code = code)
            )
            _otpState.value = when (result) {
                is Resource.Success -> Resource.Success(Unit)
                is Resource.Error -> Resource.Error(result.message ?: "Invalid OTP")
                else -> Resource.Error("Unexpected error")
            }
        }
    }
}
