package com.example.vendoapp.ui.cart

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

import android.util.Log

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    var customerId: Int = 0
    var cartId: Int = 0

    fun fetchCartItems() = viewModelScope.launch {
        Log.d("CartVM", "Fetching cart items for customer $customerId, cart $cartId")
        when(val response = repository.getCartItems(customerId, cartId)) {
            is Resource.Success -> {
                Log.d("CartVM", "Fetched ${response.data?.size ?: 0} items")
                _cartItems.value = response.data ?: emptyList()
            }
            is Resource.Error -> Log.e("CartVM", "Error fetching cart items: ${response.message}")
            else -> {}
        }
    }

    fun incrementQuantity(itemId: Int) = viewModelScope.launch {
        val item = _cartItems.value?.find { it.id == itemId } ?: return@launch
        val updatedQuantity = item.quantity + 1
        Log.d("CartVM", "Incrementing quantity for item ${item.id} to $updatedQuantity")
        when(val response = repository.updateCartItem(
            customerId,
            cartId,
            itemId,
            CartItemRequest(quantity = updatedQuantity, color = item.color, size = item.size)
        )) {
            is Resource.Success -> {
                Log.d("CartVM", "Quantity updated successfully for item ${item.id}")
                fetchCartItems()
            }
            is Resource.Error -> Log.e("CartVM", "Error updating quantity: ${response.message}")
            else -> {}
        }
    }

    fun decrementQuantity(itemId: Int) = viewModelScope.launch {
        val item = _cartItems.value?.find { it.id == itemId } ?: return@launch
        if (item.quantity <= 1) return@launch
        val updatedQuantity = item.quantity - 1
        Log.d("CartVM", "Decrementing quantity for item ${item.id} to $updatedQuantity")
        when(val response = repository.updateCartItem(
            customerId,
            cartId,
            itemId,
            CartItemRequest(quantity = updatedQuantity, color = item.color, size = item.size)
        )) {
            is Resource.Success -> {
                Log.d("CartVM", "Quantity decremented successfully for item ${item.id}")
                fetchCartItems()
            }
            is Resource.Error -> Log.e("CartVM", "Error decrementing quantity: ${response.message}")
            else -> {}
        }
    }

    fun toggleSelection(itemId: Int) {
        val updatedList = _cartItems.value?.map {
            if (it.id == itemId) {
                val newStatus = if (it.isSelected()) "UNSELECTED" else "SELECTED"
                Log.d("CartVM", "Toggling selection for item ${it.id} to $newStatus")
                it.copy(selectionStatus = newStatus)
            } else it
        }
        _cartItems.value = updatedList
    }

    fun removeItem(itemId: Int) = viewModelScope.launch {
        Log.d("CartVM", "Removing item $itemId")
        when(val response = repository.deleteCartItem(customerId, cartId, itemId)) {
            is Resource.Success -> {
                Log.d("CartVM", "Item $itemId removed successfully")
                fetchCartItems()
            }
            is Resource.Error -> Log.e("CartVM", "Error removing item: ${response.message}")
            else -> {}
        }
    }
}

