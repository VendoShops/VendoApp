package com.example.vendoapp.repository

import com.example.vendoapp.R
import com.example.vendoapp.model.home.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor() : ProductRepository {

    // Temporary mock data
    private val mockProducts = mutableListOf(
        Product(
            id = 1,
            title = "Glovy Super Gel with Advanced Night Ser..",
            imageRes = R.drawable.parfume,
            price = "$14",
            rating = 4.2,
            category = "T-shirt",
            colors = listOf("#D4AF37", "#CCCCCC", "#000000", "#FF0000", "#0000FF"),
            isFavorite = false
        ),
        Product(
            id = 2,
            title = "Glovy Super Gel with Advanced Night Ser..",
            imageRes = R.drawable.parfume,
            price = "$14",
            rating = 4.2,
            category = "T-shirt",
            colors = listOf("#D4AF37", "#CCCCCC", "#000000", "#FF0000", "#0000FF"),
            isFavorite = false
        ),
        Product(
            id = 3,
            title = "Glovy Super Gel with Advanced Night Ser..",
            imageRes = R.drawable.parfume,
            price = "$14",
            rating = 4.2,
            category = "Sweater",
            colors = listOf("#D4AF37", "#CCCCCC", "#000000", "#FF0000", "#0000FF"),
            isFavorite = false
        ),
        Product(
            id = 4,
            title = "Glovy Super Gel with Advanced Night Ser..",
            imageRes = R.drawable.parfume,
            price = "$14",
            rating = 4.2,
            category = "Pants",
            colors = listOf("#D4AF37", "#CCCCCC", "#000000", "#FF0000", "#0000FF"),
            isFavorite = false
        ),
        Product(
            id = 5,
            title = "Glovy Super Gel with Advanced Night Ser..",
            imageRes = R.drawable.parfume,
            price = "$14",
            rating = 4.2,
            category = "Dress",
            colors = listOf("#D4AF37", "#CCCCCC", "#000000", "#FF0000", "#0000FF"),
            isFavorite = false
        ),
        Product(
            id = 6,
            title = "Glovy Super Gel with Advanced Night Ser..",
            imageRes = R.drawable.parfume,
            price = "$14",
            rating = 4.2,
            category = "T-shirt",
            colors = listOf("#D4AF37", "#CCCCCC", "#000000", "#FF0000", "#0000FF"),
            isFavorite = false
        )
    )

    override suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        // API-dən və ya Database-dən melumat gondermek ucun
        // return apiService.getProducts()
        // return productDao.getAllProducts()

        // Return Mock data
        mockProducts
    }

    override suspend fun toggleFavorite(productId: String): Unit = withContext(Dispatchers.IO) {
        // API-yə və ya Database-ə yeniləmə gondermek ucun yazilacaq
        // apiService.toggleFavorite(productId)
        // productDao.updateFavorite(productId)

        // Mock data-da refreshing
        val product = mockProducts.find { it.id.toString() == productId }
        product?.let {
            val index = mockProducts.indexOf(it)
            mockProducts[index] = it.copy(isFavorite = !it.isFavorite)
        }
    }
}