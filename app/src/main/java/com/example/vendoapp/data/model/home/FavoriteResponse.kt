package com.example.vendoapp.data.model.home

data class FavoriteResponse(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val product: Product? = null
)
