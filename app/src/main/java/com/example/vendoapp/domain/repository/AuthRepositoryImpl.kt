package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordRequest
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordResponse
import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.data.remote.network.safeApiCall
import com.example.vendoapp.domain.repository.AuthRepository
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse> =
        safeApiCall { apiService.register(registerRequest) }.also { result ->
            if (result is Resource.Success) {
                result.data?.let {
                    val access = it.accessToken
                    val refresh = it.refreshToken

                    if (!access.isNullOrBlank() && !refresh.isNullOrBlank()) {
                        tokenManager.saveTokens(access, refresh)
                    }
                }
            }
        }

    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> =
        safeApiCall { apiService.login(loginRequest) }.also { result ->
            if (result is Resource.Success) {
                result.data?.let { tokenManager.saveTokens(it.accessToken, it.refreshToken) }
            }
        }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Resource<ForgotPasswordResponse> {
        return safeApiCall { apiService.forgotPassword(forgotPasswordRequest) }
    }
}