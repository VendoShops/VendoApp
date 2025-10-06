package com.example.vendoapp.domain.usecase

import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(registerRequest: RegisterRequest) = repository.register(registerRequest)
}