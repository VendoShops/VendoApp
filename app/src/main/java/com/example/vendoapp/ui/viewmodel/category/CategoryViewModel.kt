package com.example.vendoapp.ui.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendoapp.R
import com.example.vendoapp.model.category.Category
import com.example.vendoapp.model.home.Product

class CategoryViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private var originalProducts: List<Product> = emptyList()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categories.value = listOf(
            Category(1, "Beauty", R.drawable.random_image),
            Category(2, "Electronics", R.drawable.random_image),
            Category(3, "Accessories", R.drawable.random_image),
            Category(4, "Clothing", R.drawable.random_image),
            Category(5, "Shoes", R.drawable.random_image),
            Category(6, "Sports", R.drawable.random_image),
            Category(7, "Toys", R.drawable.random_image),
            Category(8, "Books", R.drawable.random_image),
            Category(9, "Home", R.drawable.random_image),
            Category(10, "Furniture", R.drawable.random_image)
        )
    }

    fun onSearchClick() {
        // Handle search click
    }

    fun onQrCodeClick() {
        // Handle QR code scanner click
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

//        if (query.isBlank()) {
//            _categories.value = originalProducts
//        } else {
//            val filteredProducts = originalProducts.filter { product ->
//                product.title.contains(query, ignoreCase = true)
//            }
//            _products.value = filteredProducts
    }
}
