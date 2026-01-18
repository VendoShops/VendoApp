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
    private val productRepository: HomeRepository,
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private var allProducts: List<Product> = emptyList()
    private var currentCategory: String? = null

    init {
        loadProducts()
    }

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            runCatching {
                productRepository.getForYouProducts()
            }

            .onSuccess { response ->
                if (response.isSuccessful) {
                    val products = response.body()?.data ?: emptyList()
                    allProducts = products
                }
                applyFilter()
            }
            .onFailure { e ->
                e.printStackTrace()
            }
        }
    }


    fun filterByCategory(category: String?) {
        currentCategory = category
        applyFilter()
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            runCatching { productRepository.toggleFavorite(product.id) }
                .onSuccess { loadProducts() }
        }
    }

    private fun applyFilter() {
        _products.value = currentCategory?.let { cat ->
            allProducts.filter { it.category.equals(cat, ignoreCase = true) }
        } ?: allProducts
    }
}