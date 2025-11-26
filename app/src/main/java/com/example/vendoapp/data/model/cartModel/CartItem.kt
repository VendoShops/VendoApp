package com.example.vendoapp.data.model.cartModel

data class CartItems(
    val title: String,
    val variant: String,
    val currentPrice: String,
    val originalPrice: String?,
    val imageResId: Int,
    val badge: Int = 0
)