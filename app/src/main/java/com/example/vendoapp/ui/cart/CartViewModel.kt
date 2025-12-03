package com.example.vendoapp.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.data.model.cartModel.CartItemRequest
import com.example.vendoapp.domain.repository.CartRepository
import com.example.vendoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>?>(null)
    val cartItems: LiveData<List<CartItem>?> = _cartItems

    private val _itemsCount = MutableLiveData<Int>(0)
    val itemsCount: LiveData<Int> = _itemsCount

    private val _subtotal = MutableLiveData<Double>(0.0)
    val subtotal: LiveData<Double> = _subtotal

    private val _discount = MutableLiveData<Double>(0.0)
    val discount: LiveData<Double> = _discount

    private val _shipping = MutableLiveData<Double>(0.0)
    val shipping: LiveData<Double> = _shipping

    private val _total = MutableLiveData<Double>(0.0)
    val total: LiveData<Double> = _total

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var customerId: Int = 0
    var cartId: Int = 0

    fun fetchCartSummary() = viewModelScope.launch {
        _isLoading.value = true
        when (val response = repository.getCartSummary(customerId, cartId)) {
            is Resource.Success -> response.data?.let { summary ->
                _cartItems.value = summary.items
                _itemsCount.value = summary.itemsCount
                _subtotal.value = summary.subtotal
                _discount.value = summary.discount
                _shipping.value = summary.shipping
                _total.value = summary.total
            }

            is Resource.Error -> Log.e("CartVM", "Error: ${response.message}")
            else -> {}
        }
        _isLoading.value = false
    }

    fun incrementQuantity(itemId: Int) = viewModelScope.launch {
        val item = _cartItems.value?.find { it.id == itemId } ?: return@launch
        val newQuantity = item.quantity + 1

        Log.d("CartVM", "Incrementing item $itemId to quantity $newQuantity")

        when (val response = repository.updateCartItem(
            customerId,
            cartId,
            itemId,
            CartItemRequest(quantity = newQuantity, color = item.color, size = item.size)
        )) {
            is Resource.Success -> {
                Log.d("CartVM", "Quantity updated successfully")
                // Backend-dən yeni summary gətir
                fetchCartSummary()
            }

            is Resource.Error -> {
                Log.e("CartVM", "Error incrementing: ${response.message}")
            }

            else -> {}
        }
    }

    fun decrementQuantity(itemId: Int) = viewModelScope.launch {
        val item = _cartItems.value?.find { it.id == itemId } ?: return@launch
        if (item.quantity <= 1) return@launch  // Minimum 1

        val newQuantity = item.quantity - 1
        Log.d("CartVM", "Decrementing item $itemId to quantity $newQuantity")

        when (val response = repository.updateCartItem(
            customerId,
            cartId,
            itemId,
            CartItemRequest(quantity = newQuantity, color = item.color, size = item.size)
        )) {
            is Resource.Success -> {
                Log.d("CartVM", "Quantity decremented successfully")
                fetchCartSummary()
            }

            is Resource.Error -> {
                Log.e("CartVM", "Error decrementing: ${response.message}")
            }

            else -> {}
        }
    }

    fun removeItem(itemId: Int) = viewModelScope.launch {
        Log.d("CartVM", "Removing item $itemId")

        when (val response = repository.deleteCartItem(customerId, cartId, itemId)) {
            is Resource.Success -> {
                Log.d("CartVM", "Item removed successfully")
                fetchCartSummary()
            }

            is Resource.Error -> {
                Log.e("CartVM", "Error removing item: ${response.message}")
            }

            else -> {}
        }
    }


    fun toggleSelection(itemId: Int) = viewModelScope.launch {
        val item = _cartItems.value?.find { it.id == itemId } ?: return@launch
        val newStatus = if (item.isSelected()) "UNSELECTED" else "SELECTED"

        Log.d("CartVM", "Toggling selection for item $itemId to $newStatus")

        when (val response =
            repository.toggleItemSelection(customerId, cartId, itemId, newStatus)) {
            is Resource.Success -> {
                Log.d("CartVM", "Selection toggled successfully")
                // Backend selection-u dəyişdikdə summary-ni də yenilə
                fetchCartSummary()
            }

            is Resource.Error -> {
                Log.e("CartVM", "Error toggling selection: ${response.message}")
            }

            else -> {}
        }
    }
}