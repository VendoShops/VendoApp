package com.example.vendoapp.domain.usecase

import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.domain.repository.ProfileRepository
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: Int): Resource<ProfileModel> {
        return repository.getUserProfile(userId)
    }
}