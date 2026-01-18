package com.example.vendoapp.data.model.home

data class Product(
    val id: Long,
    val title: String,
    val imageUrl: String?,
    val price: Double?,
    val category: String?,
    val isFavorite: Boolean,
    val rating: Double?,
    val description: String?,
    val brand: String?,
)
