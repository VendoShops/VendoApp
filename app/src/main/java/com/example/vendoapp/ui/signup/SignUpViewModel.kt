package com.example.vendoapp.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.domain.usecase.RegisterUseCase
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _registerState = MutableStateFlow<Resource<RegisterResponse>>(Resource.Idle())
    val registerState : StateFlow<Resource<RegisterResponse>> = _registerState

    fun register(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = Resource.Loading()
            Log.d("SignUpViewModel", "register() called, Loading set")
            val result = registerUseCase(RegisterRequest(fullName, email, password))
            Log.d("SignUpViewModel", "register() result=$result")
            _registerState.value = result
        }
    }
}