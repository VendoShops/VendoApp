package com.example.vendoapp.data.remote.api

import com.example.vendoapp.data.model.ApiResponse
import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
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
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("api/v1/banners")
    suspend fun getBanners(): Response<ApiResponse<List<BannerResponse>>>

    @GET("api/v1/brands/top")
    suspend fun getTopBrands(): Response<ApiResponse<List<BrandResponse>>>

    @GET("api/v1/products")
    suspend fun getAllProducts(): Response<ApiResponse<List<Product>>>

    @GET("api/v1/products/for-you")
    suspend fun getForYouProducts(): Response<ApiResponse<List<Product>>>

    @GET("api/v1/favorites")
    suspend fun getFavorites(): Response<ApiResponse<List<FavoriteResponse>>>

    @POST("api/v1/favorites/{productId}")
    suspend fun addFavorite(@Path("productId") productId: Int): Response<Unit>

    @DELETE("api/v1/favorites/{productId}")
    suspend fun removeFavorite(@Path("productId") productId: Int): Response<Unit>

    @GET("api/v1/notifications/unread-count")
    suspend fun getUnreadNotificationCount(): Response<Int>
}