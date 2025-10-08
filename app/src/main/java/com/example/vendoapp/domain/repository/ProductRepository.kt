package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.remote.api.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    //    fun getProducts(): List<Product> {
//        return apiService.getProducts()
//    }
    fun toggleFavorite(productId: String) {}
}