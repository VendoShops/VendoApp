package com.example.vendoapp.data.model.cartModel

data class CartTestModel(
    val id: String,
    var title: String,
    val price: String,
    val oldPrice: String,
    val imageRes: Int,
    val size: String,
    var isSelected: Boolean = true,
    var count: Int = 1
)
