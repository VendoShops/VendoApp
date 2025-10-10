package com.example.vendoapp.domain.usecase

import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.domain.repository.AuthRepository
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): Resource<LoginResponse> {
        return repository.login(loginRequest)
    }
}