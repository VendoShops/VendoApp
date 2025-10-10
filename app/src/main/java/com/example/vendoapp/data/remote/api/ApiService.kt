package com.example.vendoapp.data.remote.api

import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.data.model.home.BannerResponse
import com.example.vendoapp.data.model.home.BrandResponse
import com.example.vendoapp.data.model.home.FavoriteResponse
import com.example.vendoapp.data.model.home.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : RegisterResponse

    @GET("api/v1/banners")
    suspend fun getBanners(): Response<List<BannerResponse>>

    @GET("api/v1/brands")
    suspend fun getAllBrands(): Response<List<BrandResponse>>
    @GET("api/v1/brands/top")
    suspend fun getTopBrands(): Response<List<BrandResponse>>

    @GET("api/v1/products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("api/v1/products/for-you")
    suspend fun getForYouProducts(): Response<List<Product>>

    @GET("api/v1/favorites")
    suspend fun getFavorites(): Response<List<FavoriteResponse>> //productresponse

    @POST("api/v1/favorites/{productId}")
    suspend fun addFavorite(@Path("productId") productId: Int): Response<Unit>

    @DELETE("api/v1/favorites/{productId}")
    suspend fun removeFavorite(@Path("productId") productId: Int): Response<Unit>

    @GET("api/v1/notifications/unread-count")
    suspend fun getUnreadNotificationCount(): Response<Int>
}