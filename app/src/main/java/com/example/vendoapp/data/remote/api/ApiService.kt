package com.example.vendoapp.data.remote.api

import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.model.home.BannerResponse
import com.example.vendoapp.model.home.BrandResponse
import com.example.vendoapp.model.home.FavoriteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : RegisterResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : LoginResponse


    @GET("/api/v1/banners")
    suspend fun getBanners(): Response<List<BannerResponse>>

    @GET("/api/v1/brands/top")
    suspend fun getTopBrands(): Response<List<BrandResponse>>

    @GET("/api/v1/favorites")
    suspend fun getFavorites(): Response<List<FavoriteResponse>> //productresponse

    @GET("/api/v1/notifications/unread-count")
    suspend fun getUnreadNotificationCount(): Response<Int>
}