package com.example.vendoapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.home.Product
import com.example.vendoapp.data.model.home.BannerResponse
import com.example.vendoapp.data.model.home.BrandResponse
import com.example.vendoapp.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _topBrands = MutableStateFlow<List<BrandResponse>>(emptyList())
    val topBrands: StateFlow<List<BrandResponse>> get() = _topBrands

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val _banners = MutableStateFlow<List<BannerResponse>>(emptyList())
    val banners: StateFlow<List<BannerResponse>> get() = _banners

    private val _favorites = MutableStateFlow<Set<Long>>(emptySet())
    val favorites: StateFlow<Set<Long>> get() = _favorites

    private val _unreadCount = MutableStateFlow<Int?>(null)
    val unreadCount: StateFlow<Int?> get() = _unreadCount

    private val _location = MutableStateFlow("United Arab Emirates")
    val location: StateFlow<String> get() = _location

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            try {
                loadFavorites()

                // loadBrands()

                // loadBanners()

                loadProducts()

                loadUnreadCount()

            } catch (e: Exception) {
                Log.e("HomeApi", "Error loading home data", e)
                e.printStackTrace()
            }
        }
    }

    private suspend fun loadFavorites() {
        try {
            val favoritesResponse = repository.getFavorites()
            if (favoritesResponse.isSuccessful) {
                val favoritesList = favoritesResponse.body()?.data ?: emptyList()
                val favoriteIds = favoritesList.map { it.productId }.toSet()
                _favorites.value = favoriteIds
                Log.d("HomeApi", "✅ Favorites loaded: ${favoriteIds.size} items")
            } else {
                Log.e("HomeApi", "❌ Favorites failed: ${favoritesResponse.code()}")
            }
        } catch (e: Exception) {
            Log.e("HomeApi", "❌ Favorites error", e)
        }
    }

    private suspend fun loadProducts() {
        try {
            val forYouRes = repository.getForYouProducts()
            if (forYouRes.isSuccessful) {
                val productsData = forYouRes.body()?.data ?: emptyList()

                if (productsData.isEmpty()) {
                    Log.w("HomeApi", "⚠️ Products list is EMPTY - Backend has no data")
                }

                val productsWithFavorites = productsData.map { product ->
                    product.copy(isFavorite = _favorites.value.contains(product.id))
                }
                _products.value = productsWithFavorites
                Log.d("HomeApi", "✅ Products loaded: ${productsWithFavorites.size} items")
            } else {
                Log.e("HomeApi", "❌ Products failed: ${forYouRes.code()}")
            }
        } catch (e: Exception) {
            Log.e("HomeApi", "❌ Products error", e)
        }
    }

    private suspend fun loadUnreadCount() {
        try {
            val unreadResponse = repository.getUnreadCount()
            if (unreadResponse.isSuccessful) {
                _unreadCount.value = unreadResponse.body()
                Log.d("HomeApi", "✅ Unread count: ${unreadResponse.body()}")
            } else {
                Log.e("HomeApi", "❌ Unread count failed: ${unreadResponse.code()}")
            }
        } catch (e: Exception) {
            Log.e("HomeApi", "❌ Unread count error", e)
        }
    }

    fun onBrandClick(brand: BrandResponse) {
        Log.d("HomeViewModel", "Brand clicked: ${brand.name}")
    }

    fun onBannerClick(banner: BannerResponse) {
        Log.d("HomeViewModel", "Banner clicked: ${banner.title}")
    }

    fun onItemClicked(product: Product) {
        Log.d("HomeViewModel", "Item clicked: ${product.productName}")
    }

    fun onProductClick(product: Product) {
        Log.d("HomeViewModel", "Product clicked: ${product.productName}")
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            try {
                if (product.isFavorite) {
                    val response = repository.removeFavorite(product.id.toInt())
                    if (response.isSuccessful) {
                        _favorites.value -= product.id
                        Log.d("HomeViewModel", "✅ Removed from favorites: ${product.productName}")
                    }
                } else {
                    val response = repository.addFavorite(product.id.toInt())
                    if (response.isSuccessful) {
                        _favorites.value += product.id
                        Log.d("HomeViewModel", "✅ Added to favorites: ${product.productName}")
                    }
                }

                _products.value = _products.value.map {
                    if (it.id == product.id) it.copy(isFavorite = !it.isFavorite)
                    else it
                }

            } catch (e: Exception) {
                Log.e("HomeViewModel", "❌ Favorite toggle error", e)
            }
        }
    }

    fun onNotificationClick() {
        Log.d("HomeViewModel", "Notification clicked")
    }

    fun onShopNowClick() {
        Log.d("HomeViewModel", "Shop now clicked")
    }

    fun onSearchQueryChanged(query: String) {
        Log.d("HomeViewModel", "Search query: $query")
    }
}