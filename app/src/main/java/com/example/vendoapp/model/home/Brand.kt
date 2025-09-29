package com.example.vendoapp.model.home

data class Brand(
    val id: Int,
    val name: String,
    val logoRes: Int
)

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val rating: Float,
    val imageRes: Int, // drawable resource id
    val colors: List<String>, // hex color codes
    val isFavorite: Boolean = false
)
