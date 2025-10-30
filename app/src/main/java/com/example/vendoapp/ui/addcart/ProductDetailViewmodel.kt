package com.example.vendoapp.ui.addcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendoapp.data.model.home.ProductColorItem
import com.example.vendoapp.data.model.home.ProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor() : ViewModel() {

    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail: LiveData<ProductDetail> = _productDetail

    private val _selectedColor = MutableLiveData<String>()
    val selectedColor: LiveData<String> = _selectedColor

    private val _selectedSize = MutableLiveData<String>()
    val selectedSize: LiveData<String> = _selectedSize

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _addToCartSuccess = MutableLiveData<Boolean>()
    val addToCartSuccess: LiveData<Boolean> = _addToCartSuccess

    init {
        loadDemoData()
    }

    private fun loadDemoData() {
        _productDetail.value = ProductDetail(
            id = "1",
            name = "Glovy",
            description = "Super Gel with Advanced Night Ser Kadin Bordo",
            price = 9.0,
            rating = 4.3,
            reviewCount = 144,
            qaCount = 285,
            storeName = "Cavidstore",
            isVerified = true,
            images = listOf(
                "https://via.placeholder.com/400x400/F5F5DC/000000?text=Image+1",
                "https://via.placeholder.com/400x400/FFB6C1/000000?text=Image+2",
                "https://via.placeholder.com/400x400/ADD8E6/000000?text=Image+3",
                "https://via.placeholder.com/400x400/90EE90/000000?text=Image+4",
                "https://via.placeholder.com/400x400/FFFFE0/000000?text=Image+5"
            ),
            colors = listOf(
                ProductColorItem("White", "https://via.placeholder.com/80x100/FFFFFF/000000?text=White"),
                ProductColorItem("Red", "https://via.placeholder.com/80x100/FF0000/FFFFFF?text=Red"),
                ProductColorItem("Green", "https://via.placeholder.com/80x100/00FF00/000000?text=Green"),
                ProductColorItem("Gray", "https://via.placeholder.com/80x100/808080/FFFFFF?text=Gray")
            ),
            defaultColor = "",  // No default color - user must select
            sizes = listOf("M", "L", "XL", "XXL"),
            defaultSize = "M",
            estimatedDelivery = "20-30 January",
            shippingInfo = "Free shipping on concerns over $25",
            shippingPrice = "Free shipping",
            returnInfo = "Easy and free return in 7 days from the delivered date"
        )

        // Start with empty selected color - user must select
        _selectedColor.value = ""
        _selectedSize.value = "M"
        _isFavorite.value = false
    }

    fun loadProduct(productId: String) {
        // TODO: API call
        // repository.getProductDetail(productId).observe { result ->
        //     when (result) {
        //         is Success -> {
        //             _productDetail.value = result.data
        //             _selectedColor.value = ""  // Reset color selection
        //             _selectedSize.value = result.data.defaultSize
        //         }
        //         is Error -> // Handle error
        //     }
        // }
    }

    fun selectColor(colorItem: ProductColorItem) {
        _selectedColor.value = colorItem.colorName
    }

    fun selectSize(size: String) {
        _selectedSize.value = size
    }

    fun toggleFavorite() {
        _isFavorite.value = !(_isFavorite.value ?: false)
        // TODO: API call to add/remove from favorites
        // repository.toggleFavorite(productDetail.value?.id)
    }

    fun addToCart() {
        val product = _productDetail.value ?: return
        val color = _selectedColor.value
        val size = _selectedSize.value ?: return

        // Validate color selection
        if (color.isNullOrEmpty()) {
            // Show error - color must be selected
            // You can add a separate LiveData for showing errors
            return
        }

        // TODO: API call to add to cart
        // repository.addToCart(product.id, color, size, quantity = 1)

        _addToCartSuccess.value = true
    }

    fun resetAddToCartFlag() {
        _addToCartSuccess.value = false
    }
}