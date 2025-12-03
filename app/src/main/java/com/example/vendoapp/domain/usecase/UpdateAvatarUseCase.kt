package com.example.vendoapp.domain.usecase

import com.example.vendoapp.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UpdateAvatarUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: Int, part: MultipartBody.Part) =
        repository.updateUserProfileImage(userId, part)
}