package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.data.model.profile.personalinformation.UpdateProfileRequest
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.data.remote.network.safeApiCall
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProfileRepository {

    override suspend fun getUserProfile(userId: Int): Resource<ProfileModel> {
        return safeApiCall { apiService.getUserProfile(userId) }
    }

    override suspend fun updateUserProfile(userId: Int, request: UpdateProfileRequest): Resource<ProfileModel> {
        return safeApiCall { apiService.updateUserProfile(userId, request) }
    }
}