package com.example.vendoapp.domain.usecase

import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordRequest
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordResponse
import com.example.vendoapp.domain.repository.AuthRepository
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(forgotPasswordRequest: ForgotPasswordRequest): Resource<ForgotPasswordResponse> {
        return repository.forgotPassword(forgotPasswordRequest)
    }
}