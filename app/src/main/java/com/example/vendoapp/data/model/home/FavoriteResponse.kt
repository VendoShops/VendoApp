package com.example.vendoapp.data.model.home

data class FavoriteResponse(
    val id: Long,
    val userId: Long,
    val product: Product? = null,
    val productRecommendationResponse: ProductRecommendationResponse
)

data class ProductRecommendationResponse(
    val id: Long,
)
