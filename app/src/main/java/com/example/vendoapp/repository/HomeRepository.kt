package com.example.vendoapp.repository

import com.example.vendoapp.remote.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getBanners() = api.getBanners()
    suspend fun getTopBrands() = api.getTopBrands()
    suspend fun getFavorites() = api.getFavorites()
    suspend fun getUnreadCount() = api.getUnreadNotificationCount()
}