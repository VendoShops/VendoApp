package com.example.vendoapp.data.model.home

data class ProductResponse(
    val id: Long,
    val title: String?,
    val price: String?,
    val rating: Double?,
    val category: String?,
    val imageUrl: String?,
    val colors: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
