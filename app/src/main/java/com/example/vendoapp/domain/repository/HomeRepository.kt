package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.remote.api.ApiService
import javax.inject.Inject

interface HomeRepository {
    val api: ApiService

    suspend fun getBanners() = api.getBanners()
    suspend fun getTopBrands() = api.getTopBrands()

    suspend fun getForYouProducts() = api.getForYouProducts()

    suspend fun addFavorite(productId: Int) = api.addFavorite(productId)

    suspend fun removeFavorite(productId: Int) = api.removeFavorite(productId)
    suspend fun getFavorites() = api.getFavorites()
    suspend fun getUnreadCount() = api.getUnreadNotificationCount()
}