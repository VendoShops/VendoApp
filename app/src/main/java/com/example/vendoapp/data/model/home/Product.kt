package com.example.vendoapp.data.model.home

data class Product(
    val id: Int,
    val title: String?,
    val price: Double?,
    val rating: Double?,
    val category: String?,
    val imageUrl: String?, // drawable resource id
    val colors: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
