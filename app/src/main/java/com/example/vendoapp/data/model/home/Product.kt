package com.example.vendoapp.data.model.home

data class Product(
    val id: Long,
    val productName: String?,
    val price: Double?,
    val rating: Double?,
    val imageUrl: String?,
    var isFavorite: Boolean = false
)
