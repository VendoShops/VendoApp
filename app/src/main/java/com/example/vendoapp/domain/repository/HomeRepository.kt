package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.remote.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val api: ApiService,
) {

    suspend fun getBanners() = api.getBanners()

    suspend fun getTopBrands() = api.getTopBrands()

    suspend fun getForYouProducts() = api.getForYouProducts()

    suspend fun addFavorite(productId: Int) = api.addFavorite(productId)

    suspend fun removeFavorite(productId: Int) = api.removeFavorite(productId)

    suspend fun getFavorites() = api.getFavorites()

    suspend fun getUnreadCount() = api.getUnreadNotificationCount()

    suspend fun toggleFavorite(productId: Long) {
        val response = getFavorites()
        if (!response.isSuccessful) return

        val favorites = response.body().orEmpty()

        val isFavorite = favorites.any { it.productRecommendationResponse.id == productId }
        if (isFavorite) {
            removeFavorite(productId.toInt())
        } else {
            addFavorite(productId.toInt())
        }
    }

}