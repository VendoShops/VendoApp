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

    var customerId: Int = 0
    var cartId: Int = 0

    fun fetchCartItems() = viewModelScope.launch {
        Log.d("CartVM", "Fetching cart items for customer $customerId, cart $cartId")
        when(val response = repository.getCartItems(customerId, cartId)) {
            is Resource.Success -> {
                Log.d("CartVM", "Fetched ${response.data?.size ?: 0} items")
                _cartItems.value = response.data ?: emptyList()
                computeSummary()
            }
            is Resource.Error -> Log.e("CartVM", "Error fetching cart items: ${response.message}")
            else -> {}
        }
    }

    private fun computeSummary() {
        val items = _cartItems.value ?: emptyList()
        val selected = items.filter { it.isSelected() }

        val itemsCount = selected.sumOf { it.quantity }
        val originalTotal = selected.sumOf { it.productPrice * it.quantity }
        val discountedTotal = selected.sumOf { (it.discountPrice ?: it.productPrice) * it.quantity }

        val discount = (originalTotal - discountedTotal).coerceAtLeast(0.0)
        val shipping = if (discountedTotal > 0.0) 6.0 else 0.0
        val total = discountedTotal + shipping

        _itemsCount.value = itemsCount
        _subtotal.value = discountedTotal
        _discount.value = discount
        _shipping.value = shipping
        _total.value = total

        Log.d("CartVM", "Summary computed: items=$itemsCount, subtotal=$discountedTotal, discount=$discount, shipping=$shipping, total=$total")
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
        computeSummary()
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
