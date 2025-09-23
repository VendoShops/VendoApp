package com.example.vendoapp.ui.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendoapp.R
import com.example.vendoapp.model.home.Brand
import com.example.vendoapp.model.home.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _topBrands = MutableLiveData<List<Brand>>()
    val topBrands: LiveData<List<Brand>> = _topBrands

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> = _location

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private var originalProducts: List<Product> = emptyList()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        // Mock top brands data
        _topBrands.value = listOf(
            Brand(1, "Sephora", R.drawable.nike_image),
            Brand(2, "Adidas", R.drawable.nike_image),
            Brand(3, "Nike", R.drawable.nike_image),
            Brand(4, "Guess", R.drawable.nike_image),
            Brand(5, "H&M", R.drawable.nike_image),
            Brand(6, "Zara", R.drawable.nike_image)
        )

        // Mock products data
        val mockProducts = listOf(
            Product(
                id = 1,
                title = "Glovy Super Gel with Advanced Night Ser..",
                price = "$14",
                rating = 4.2f,
                imageRes = R.drawable.parfume,
                colors = listOf("#D4AF37", "#C0C0C0", "#8B4513", "#000000", "#FFFFFF"),
                isFavorite = false
            ),
            Product(
                id = 2,
                title = "Glovy Super Gel with Advanced Night Ser..",
                price = "$14",
                rating = 4.2f,
                imageRes = R.drawable.parfume,
                colors = listOf("#D4AF37", "#C0C0C0", "#8B4513", "#FF6B6B", "#4ECDC4"),
                isFavorite = false
            )
        )

        originalProducts = mockProducts
        _products.value = mockProducts
        _location.value = "United Arab Emirates"
    }

    fun onBrandClick(brand: Brand) {
        // Handle brand click
    }

    fun onProductClick(product: Product) {
        // Handle product click
    }

    fun onFavoriteClick(product: Product) {
        val currentProducts = _products.value ?: return
        val updatedProducts = currentProducts.map {
            if (it.id == product.id) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        _products.value = updatedProducts
    }

    fun onSearchClick() {
        // Handle search click
    }

    fun onNotificationClick() {
        // Handle notification click
    }

    fun onShopNowClick() {
        // Handle shop now click
    }

    fun onQrCodeClick() {
        // Handle QR code scanner click
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            _products.value = originalProducts
        } else {
            val filteredProducts = originalProducts.filter { product ->
                product.title.contains(query, ignoreCase = true)
            }
            _products.value = filteredProducts
        }
    }
}