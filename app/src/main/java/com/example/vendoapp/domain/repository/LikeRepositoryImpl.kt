package com.example.vendoapp.domain.repository

import android.util.Log
import com.example.vendoapp.data.model.home.FavoriteResponse
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LikeRepository {

    override suspend fun getMyFavorites(): Resource<List<FavoriteResponse>> {
        Log.d("LIKE_REPO", "getMyFavorites() çağırılır")
        return safeApiCall {
            Log.d("LIKE_REPO", "API çağırılır: getFavorites()")
            val response = apiService.getFavorites()
            Log.d("LIKE_REPO", "API Response code: ${response.code()}")
            Log.d("LIKE_REPO", "API Response isSuccessful: ${response.isSuccessful}")
            Log.d("LIKE_REPO", "API Response body: ${response.body()}")
            Log.d("LIKE_REPO", "API Response data: ${response.body()?.data}")

            val result = response.body()?.data ?: emptyList()
            Log.d("LIKE_REPO", "Extracted favorites count: ${result.size}")
            result.forEachIndexed { index, favorite ->
                Log.d("LIKE_REPO", "Favorite $index: id=${favorite.id}, productId=${favorite.productId}, product=${favorite.product}")
            }
            result
        }
    }

    override suspend fun addFavorite(productId: Int): Resource<FavoriteResponse> {
        Log.d("LIKE_REPO", "addFavorite() çağırılır: productId=$productId")
        return safeApiCall {
            Log.d("LIKE_REPO", "API çağırılır: addFavorite($productId)")
            val response = apiService.addFavorite(productId)
            Log.d("LIKE_REPO", "AddFavorite Response code: ${response.code()}")
            Log.d("LIKE_REPO", "AddFavorite Response isSuccessful: ${response.isSuccessful}")
            Log.d("LIKE_REPO", "AddFavorite Response body: ${response.body()}")

            val result = response.body() ?: throw Exception("Add favorite failed")
            Log.d("LIKE_REPO", "AddFavorite success: $result")
            result
        }
    }

    override suspend fun removeFavorite(productId: Int): Resource<Unit> {
        Log.d("LIKE_REPO", "removeFavorite() çağırılır: productId=$productId")
        return safeApiCall {
            Log.d("LIKE_REPO", "API çağırılır: removeFavorite($productId)")
            val response = apiService.removeFavorite(productId)
            Log.d("LIKE_REPO", "RemoveFavorite Response code: ${response.code()}")
            Log.d("LIKE_REPO", "RemoveFavorite Response isSuccessful: ${response.isSuccessful}")

            if (!response.isSuccessful) {
                Log.e("LIKE_REPO", "RemoveFavorite failed: ${response.code()}")
                throw Exception("Remove favorite failed")
            }
            Log.d("LIKE_REPO", "RemoveFavorite success")
            Unit
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Log.d("LIKE_REPO", "safeApiCall başladı")
            val result = apiCall()
            Log.d("LIKE_REPO", "safeApiCall uğurla bitdi: ${result.toString().take(100)}")
            Resource.Success(result)
        } catch (e: Exception) {
            Log.e("LIKE_REPO", "safeApiCall xəta: ${e.message}", e)
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }
}