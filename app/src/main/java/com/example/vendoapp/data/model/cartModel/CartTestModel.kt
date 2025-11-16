package com.example.vendoapp.data.model.cartModel

data class CartTestModel(
    var title: String,
    val price: String,
    val oldPrice: String,
    val imageRes: Int,
    val size: String,
    var isFavorite: Boolean
)
