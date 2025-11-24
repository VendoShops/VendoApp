package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.home.FavoriteResponse
import com.example.vendoapp.utils.Resource

interface LikeRepository {
    suspend fun getMyFavorites(): Resource<List<FavoriteResponse>>
    suspend fun addFavorite(productId: Int): Resource<FavoriteResponse>
    suspend fun removeFavorite(productId: Int): Resource<Unit>
}