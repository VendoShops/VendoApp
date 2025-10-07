package com.example.vendoapp.repository

import com.example.vendoapp.model.home.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun toggleFavorite(productId: String)
}