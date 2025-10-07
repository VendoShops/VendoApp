package com.example.vendoapp.model.home

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val rating: Double,
    val category: String,
    val imageRes: Int, // drawable resource id
    val colors: List<String>, // hex color codes
    val isFavorite: Boolean = false
)
