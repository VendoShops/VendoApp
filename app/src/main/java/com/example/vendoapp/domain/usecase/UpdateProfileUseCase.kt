package com.example.vendoapp.domain.usecase

import com.example.vendoapp.data.model.profile.personalinformation.UpdateProfileRequest
import com.example.vendoapp.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: Int, request: UpdateProfileRequest) =
        repository.updateUserProfile(userId, request)
}