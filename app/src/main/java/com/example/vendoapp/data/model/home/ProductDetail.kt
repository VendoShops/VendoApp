package com.example.vendoapp.data.model.home

data class ProductDetail(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val reviewCount: Int,
    val qaCount: Int,
    val storeName: String,
    val isVerified: Boolean,
    val images: List<String>,
    val colors: List<ProductColorItem>,
    val defaultColor: String,
    val sizes: List<String>,
    val defaultSize: String,
    val estimatedDelivery: String,
    val shippingInfo: String,
    val shippingPrice: String,
    val returnInfo: String
)
