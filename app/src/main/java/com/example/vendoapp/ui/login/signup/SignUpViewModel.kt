package com.example.vendoapp.ui.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.domain.usecase.RegisterUseCase
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _registerState = MutableStateFlow<Resource<RegisterResponse>>(Resource.Idle())
    val registerState : StateFlow<Resource<RegisterResponse>> = _registerState

    fun register(fullName: String, email: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            _registerState.value = Resource.Loading()
            val result = registerUseCase(RegisterRequest(fullName, email, password))
            _registerState.value = result

            if (result is Resource.Success && remember) {
                saveLoginData(
                    email,
                    password,
                    result.data?.accessToken,
                    result.data?.refreshToken,
                    result.data?.accessTokenExpiryDate,
                    result.data?.refreshTokenExpiryDate
                )
            }
        }
    }

    fun saveLoginData(
        email: String, password: String, accessToken: String?,
        refreshToken: String?, accessExpiry: String?, refreshExpiry: String?
    ) {
        tokenManager.saveTokens(accessToken, refreshToken, accessExpiry, refreshExpiry)
        tokenManager.saveUserCredentials(email, password)
    }
}