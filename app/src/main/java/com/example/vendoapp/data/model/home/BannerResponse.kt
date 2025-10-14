package com.example.vendoapp.data.model.home

// /api/v1/banners

data class BannerResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val targetUrl: String?
)
