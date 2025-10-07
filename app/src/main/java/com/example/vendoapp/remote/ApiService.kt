package com.example.vendoapp.remote

import com.example.vendoapp.model.home.BannerResponse
import com.example.vendoapp.model.home.BrandResponse
import com.example.vendoapp.model.home.FavoriteResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/api/v1/banners")
    suspend fun getBanners(): Response<List<BannerResponse>>

    @GET("/api/v1/brands/top")
    suspend fun getTopBrands(): Response<List<BrandResponse>>

    @GET("/api/v1/favorites")
    suspend fun getFavorites(): Response<List<FavoriteResponse>> //productresponse

    @GET("/api/v1/notifications/unread-count")
    suspend fun getUnreadNotificationCount(): Response<Int>
}