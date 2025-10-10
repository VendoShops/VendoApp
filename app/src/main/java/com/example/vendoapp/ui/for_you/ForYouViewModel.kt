package com.example.vendoapp.ui.for_you

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.home.Product
import com.example.vendoapp.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val productRepository: HomeRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        loadProducts()
    }

    private var allProducts: List<Product> = emptyList()
    private var currentCategory: String = "T-shirt"

    init {
        loadProducts()
    }

    private fun loadProducts() {
//        viewModelScope.launch {
//            try {
//                allProducts = productRepository.getProducts()
//                filterByCategory(currentCategory)
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
    }

    fun filterByCategory(category: String) {
        currentCategory = category
//        val filteredProducts = allProducts.filter {
//            it.category.equals(category, ignoreCase = true)
//        }
//        _products.value = filteredProducts
    }

    fun toggleFavorite(product: com.example.vendoapp.data.model.home.Product) { //product-->productresponse
        viewModelScope.launch {
//            try {
//                productRepository.toggleFavorite(product.id.toString())
//                loadProducts()
//            } catch (e: Exception) {
//                // Handle error
//            }
        }
    }
}