package com.example.vendoapp.ui.like

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.home.Product
import com.example.vendoapp.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _favorites = MutableStateFlow<Set<Long>>(emptySet())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val likedProducts: StateFlow<List<Product>> = combine(
        _products,
        _favorites
    ) { products, favorites ->
        products.filter { favorites.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        refresh()
    }

    fun loadProductsAndFavorites() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                // 1) Favorites
                val favResp = try { repository.getFavorites() } catch (e: Exception) {
                    _error.value = "Favorites request failed: ${e.localizedMessage ?: e.toString()}"
                    Log.e("LikeVM", "Favorites request exception", e)
                    _loading.value = false
                    return@launch
                }

                if (favResp.isSuccessful) {
                    val favList = favResp.body()?.data ?: emptyList()
                    _favorites.value = favList.map { it.productId }.toSet()
                    Log.d("LikeVM", "Favorites loaded: ${_favorites.value.size}")
                } else {
                    _error.value = "Favorites API error: ${favResp.code()}"
                    Log.e("LikeVM", "Favorites failed: ${favResp.code()}")
                    _loading.value = false
                    return@launch
                }

                val prodResp = try { repository.getForYouProducts() } catch (e: Exception) {
                    _error.value = "Products request failed: ${e.localizedMessage ?: e.toString()}"
                    Log.e("LikeVM", "Products request exception", e)
                    _loading.value = false
                    return@launch
                }

                if (prodResp.isSuccessful) {
                    val productsData = prodResp.body()?.data ?: emptyList()
                    // mark favorites in product objects
                    val productsWithFav = productsData.map { p ->
                        p.copy(isFavorite = _favorites.value.contains(p.id))
                    }
                    _products.value = productsWithFav
                    Log.d("LikeVM", "Products loaded: ${productsWithFav.size}")
                } else {
                    _error.value = "Products API error: ${prodResp.code()}"
                    Log.e("LikeVM", "Products failed: ${prodResp.code()}")
                    _loading.value = false
                    return@launch
                }

                _error.value = null

            } catch (e: Exception) {
                _error.value = "Unexpected error: ${e.localizedMessage ?: e.toString()}"
                Log.e("LikeVM", "loadProductsAndFavorites error", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun refresh() {
        loadProductsAndFavorites()
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            try {
                val wasFavorite = product.isFavorite
                _products.value = _products.value.map {
                    if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it
                }
                _favorites.value = if (wasFavorite) _favorites.value - product.id else _favorites.value + product.id

                if (wasFavorite) {
                    val resp = repository.removeFavorite(product.id.toInt())
                    if (!resp.isSuccessful) {
                        _products.value = _products.value.map {
                            if (it.id == product.id) it.copy(isFavorite = wasFavorite) else it
                        }
                        _favorites.value = if (wasFavorite) _favorites.value + product.id else _favorites.value - product.id
                        _error.value = "Remove favorite failed: ${resp.code()}"
                        Log.e("LikeVM", "Remove favorite failed: ${resp.code()}")
                    }
                } else {
                    val resp = repository.addFavorite(product.id.toInt())
                    if (!resp.isSuccessful) {
                        _products.value = _products.value.map {
                            if (it.id == product.id) it.copy(isFavorite = wasFavorite) else it
                        }
                        _favorites.value = if (wasFavorite) _favorites.value + product.id else _favorites.value - product.id
                        _error.value = "Add favorite failed: ${resp.code()}"
                        Log.e("LikeVM", "Add favorite failed: ${resp.code()}")
                    }
                }
            } catch (e: Exception) {
                _error.value = "Favorite toggle error: ${e.localizedMessage ?: e.toString()}"
                Log.e("LikeVM", "favorite toggle error", e)
            }
        }
    }

    fun onProductClick(product: Product) {
        Log.d("LikeVM", "Product clicked: ${product.productName}")
    }
}
