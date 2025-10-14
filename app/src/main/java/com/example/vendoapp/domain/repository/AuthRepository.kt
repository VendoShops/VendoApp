package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordRequest
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordResponse
import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.utils.Resource

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest) : Resource<RegisterResponse>

    suspend fun login(loginRequest: LoginRequest) : Resource<LoginResponse>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) : Resource<ForgotPasswordResponse>
}

