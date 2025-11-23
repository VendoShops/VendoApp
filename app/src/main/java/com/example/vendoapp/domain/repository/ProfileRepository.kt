package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.data.model.profile.personalinformation.UpdateProfileRequest
import com.example.vendoapp.utils.Resource

interface ProfileRepository {
    suspend fun getUserProfile(userId: Int): Resource<ProfileModel>
    suspend fun updateUserProfile(userId: Int, request: UpdateProfileRequest): Resource<ProfileModel>
}