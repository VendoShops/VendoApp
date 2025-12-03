package com.example.vendoapp.ui.like

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.home.Product
import com.example.vendoapp.domain.repository.LikeRepository
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val repository: LikeRepository
) : ViewModel() {

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts: StateFlow<List<Product>> = _favoriteProducts.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        Log.d("LIKE_VM", "LikeViewModel init edildi")
        loadFavorites()
    }

    fun loadFavorites() {
        Log.d("LIKE_VM", "loadFavorites() çağırılır")
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            Log.d("LIKE_VM", "Loading started")

            when (val result = repository.getMyFavorites()) {
                is Resource.Success -> {
                    Log.d("LIKE_VM", "getMyFavorites SUCCESS")
                    val favorites = result.data ?: emptyList()
                    Log.d("LIKE_VM", "Raw favorites count: ${favorites.size}")

                    // FavoriteResponse-ları Product-a çevirin
                    val products = favorites.mapNotNull { favorite ->
                        Log.d("LIKE_VM", "Processing favorite: ${favorite.id}, product=${favorite.product}")
                        favorite.product?.copy(isFavorite = true)
                    }
                    Log.d("LIKE_VM", "Mapped products count: ${products.size}")
                    _favoriteProducts.value = products

                    if (products.isEmpty()) {
                        Log.d("LIKE_VM", "No products found in favorites")
                    }
                }
                is Resource.Error -> {
                    Log.e("LIKE_VM", "getMyFavorites ERROR: ${result.message}")
                    _error.value = result.message
                }
                else -> {
                    Log.d("LIKE_VM", "getMyFavorites UNKNOWN RESULT")
                }
            }
            _loading.value = false
            Log.d("LIKE_VM", "Loading finished")
        }
    }

    fun onFavoriteClick(product: Product) {
        Log.d("LIKE_VM", "onFavoriteClick: productId=${product.id}, isFavorite=${product.isFavorite}")
        viewModelScope.launch {
            val productId = product.id.toInt()

            if (product.isFavorite) {
                Log.d("LIKE_VM", "Removing favorite: $productId")
                when (val result = repository.removeFavorite(productId)) {
                    is Resource.Success -> {
                        Log.d("LIKE_VM", "Remove favorite SUCCESS")
                        // UI-da sil
                        _favoriteProducts.value = _favoriteProducts.value.filter { it.id != product.id }
                        Log.d("LIKE_VM", "Product removed from UI, new count: ${_favoriteProducts.value.size}")
                    }
                    is Resource.Error -> {
                        Log.e("LIKE_VM", "Remove favorite ERROR: ${result.message}")
                        _error.value = "Remove failed: ${result.message}"
                    }
                    else -> {
                        Log.d("LIKE_VM", "Remove favorite UNKNOWN RESULT")
                    }
                }
            } else {
                Log.d("LIKE_VM", "Adding favorite: $productId")
                when (val result = repository.addFavorite(productId)) {
                    is Resource.Success -> {
                        Log.d("LIKE_VM", "Add favorite SUCCESS: ${result.data}")
                        result.data?.product?.let { newProduct ->
                            _favoriteProducts.value += newProduct.copy(isFavorite = true)
                            Log.d("LIKE_VM", "Product added to UI, new count: ${_favoriteProducts.value.size}")
                        } ?: Log.e("LIKE_VM", "Add favorite success but product is null")
                    }
                    is Resource.Error -> {
                        Log.e("LIKE_VM", "Add favorite ERROR: ${result.message}")
                        _error.value = "Add failed: ${result.message}"
                    }
                    else -> {
                        Log.d("LIKE_VM", "Add favorite UNKNOWN RESULT")
                    }
                }
            }
        }
    }

    fun onProductClick(product: Product) {
        Log.d("LIKE_VM", "Product clicked: ${product.productName}, id=${product.id}")
        // Navigate to product detail
    }

    fun refresh() {
        Log.d("LIKE_VM", "refresh() çağırılır")
        loadFavorites()
    }

    // Test funksiyası
    fun testApiCalls() {
        Log.d("LIKE_VM", "=== API TEST BAŞLADI ===")
        viewModelScope.launch {
            Log.d("LIKE_VM", "1. getMyFavorites test...")
            val result = repository.getMyFavorites()
            when (result) {
                is Resource.Success -> Log.d("LIKE_VM", "TEST SUCCESS: ${result.data?.size ?: 0} favorites")
                is Resource.Error -> Log.e("LIKE_VM", "TEST ERROR: ${result.message}")
                else -> {}
            }
            Log.d("LIKE_VM", "=== API TEST BİTDİ ===")
        }
    }
}