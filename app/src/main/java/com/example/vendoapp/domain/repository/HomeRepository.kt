package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.remote.api.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getBanners() = api.getBanners()
    suspend fun getTopBrands() = api.getTopBrands()
    suspend fun getFavorites() = api.getFavorites()
    suspend fun getUnreadCount() = api.getUnreadNotificationCount()
}