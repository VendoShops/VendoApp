package com.example.vendoapp.model.home

// /api/v1/banners

data class BannerResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val targetUrl: String?
)
