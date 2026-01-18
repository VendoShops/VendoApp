package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.home.FavoriteRequest
import com.example.vendoapp.data.model.home.FavoriteResponse
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LikeRepository {

    override suspend fun getMyFavorites(): Resource<List<FavoriteResponse>> {
        return safeApiCall {
            val response = apiService.getFavorites()
            if (!response.isSuccessful) {
                throw Exception("Network error: ${response.code()} ${response.message()}")
            }
            response.body().orEmpty()
        }
    }

    override suspend fun addFavorite(productId: Int): Resource<FavoriteResponse> {
        // Swagger POST response schema göstərmir (sadəcə 200 description):contentReference[oaicite:16]{index=16}
        // Ona görə: add-dan sonra list-i yenilə və son vəziyyəti qaytar.
        return safeApiCall {
            val post = apiService.addFavorite(productId)
            if (!post.isSuccessful) {
                throw Exception("Add favorite failed: ${post.code()} ${post.message()}")
            }

            // yenilə
            val listResp = apiService.getFavorites()
            if (!listResp.isSuccessful) {
                throw Exception("Reload favorites failed: ${listResp.code()} ${listResp.message()}")
            }

            // Ən sadə qaytarma: həmin productId-li favorit entry-ni tap
            val list = listResp.body().orEmpty()
            list.firstOrNull { it.productRecommendationResponse.id == productId.toLong() }
                ?: throw Exception("Favorite added but not found in list")
        }
    }

    override suspend fun removeFavorite(productId: Int): Resource<Unit> {
        return safeApiCall {
            val response = apiService.removeFavorite(productId)
            if (!response.isSuccessful) {
                throw Exception("Remove favorite failed: ${response.code()} ${response.message()}")
            }
            Unit
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }
}
