package com.example.vendoapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.home.Brand
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
    private val repository: HomeRepository
) : ViewModel() {

    private val _topBrands = MutableStateFlow<List<BrandResponse>>(emptyList())
    val topBrands: StateFlow<List<BrandResponse>> get() = _topBrands

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val _banners = MutableStateFlow<List<BannerResponse>>(emptyList())
    val banners: StateFlow<List<BannerResponse>> get() = _banners

    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> get() = _favorites

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
                // Fetch data from API for brands
                val brandsResponse = repository.getTopBrands()
                if (brandsResponse.isSuccessful) {
                    _topBrands.value = brandsResponse.body() ?: emptyList()
                } else {
                    android.util.Log.e(
                        "HomeApi",
                        "Brands failed: ${brandsResponse.code()} - ${brandsResponse.message()}"
                    )
                }

                // Fetch data from API for products
                val bannerResponse = repository.getBanners()
                if (bannerResponse.isSuccessful) {
                    _banners.value = bannerResponse.body() ?: emptyList()
                } else {
                    android.util.Log.e(
                        "HomeApi",
                        "Banners failed: ${bannerResponse.code()} - ${bannerResponse.message()}"
                    )
                }

                val forYouRes = repository.getForYouProducts()
                if (forYouRes.isSuccessful) _products.value = forYouRes.body() ?: emptyList()

                val unreadResponse = repository.getUnreadCount()
                if (unreadResponse.isSuccessful) {
                    _unreadCount.value = unreadResponse.body()
                } else {
                    android.util.Log.e(
                        "HomeApi",
                        "Unread count failed: ${unreadResponse.code()} - ${unreadResponse.message()}"
                    )
                }

                // Fetch data from API for favorites
                val favoritesResponse = repository.getFavorites()
                if (favoritesResponse.isSuccessful) {
                    // Map FavoriteResponse to ProductResponse - extract nested product
                    val favoritesList = favoritesResponse.body() ?: emptyList()
                    _favorites.value = favoritesList.mapNotNull { it.product }
                } else {
                    android.util.Log.e(
                        "HomeApi",
                        "Favorites failed: ${favoritesResponse.code()} - ${favoritesResponse.message()}"
                    )
                }

            } catch (e: Exception) {
                android.util.Log.e("HomeApi", "Error loading home data", e)
                e.printStackTrace()
            }
        }
    }

    fun onBrandClick(brand: Brand) {
        // navigate or filter logic
    }

    fun onBannerClick(banner: BannerResponse) {
        // navigate or detail logic
    }

    fun onItemClicked(product: Product) {
        // navigate or detail logic
    }

    fun onProductClick(product: Product) {
        // navigate or detail logic
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            try {
                if (product.isFavorite) {
                    repository.removeFavorite(product.id)
                } else {
                    repository.addFavorite(product.id)
                }

                _products.value = _products.value.map {
                    if (it.id == product.id) it.copy(isFavorite = !it.isFavorite)
                    else it
                }

            } catch (e: Exception) {
                Log.e("HomeViewModel", "Favorite toggle error", e)
            }
        }
    }

    fun onNotificationClick() {
        // navigate to notifications
    }

    fun onShopNowClick() {
        // navigate to shop page
    }

    fun onSearchQueryChanged(query: String) {
        // filter logic or search API
    }
}