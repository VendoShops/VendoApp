package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.data.remote.network.safeApiCall
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse> =
        safeApiCall { apiService.register(registerRequest) }

    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> =
        safeApiCall { apiService.login(loginRequest) }
}
