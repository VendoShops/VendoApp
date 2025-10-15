package com.example.vendoapp.ui.login.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordRequest
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordResponse
import com.example.vendoapp.domain.usecase.ForgotPasswordUseCase
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    private val _forgotPasswordState = MutableStateFlow<Resource<ForgotPasswordResponse>>(Resource.Idle())
    val forgotPasswordState: StateFlow<Resource<ForgotPasswordResponse>> = _forgotPasswordState

    fun sendResetPassword(email: String) {
        if (email.isBlank()) {
            _forgotPasswordState.value = Resource.Error("Email cannot be empty")
            return
        }

        viewModelScope.launch {
            _forgotPasswordState.value = Resource.Loading()
            val result = forgotPasswordUseCase(ForgotPasswordRequest(email))
            _forgotPasswordState.value = result
        }
    }
}
