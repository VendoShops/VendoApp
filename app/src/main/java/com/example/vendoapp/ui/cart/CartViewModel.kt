package com.example.vendoapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendoapp.R
import com.example.vendoapp.data.model.cartModel.CartTestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartTestModel>>()
    val cartItems: LiveData<List<CartTestModel>> = _cartItems

    private val _countMap = MutableLiveData<MutableMap<Int, Int>>()
    val countMap: LiveData<MutableMap<Int, Int>> = _countMap



    init {
        if (_cartItems.value.isNullOrEmpty()) {
            _cartItems.value = listOf(
                CartTestModel("1", "Nike Air Max", "$120", "$150", R.drawable.test_girl, "US 10", true),
                CartTestModel("2", "Adidas Superstar", "$85", "$110", R.drawable.test_girl, "US 9", true),
                CartTestModel("3", "Puma RS-X", "$95", "$130", R.drawable.test_girl, "US 11", true)
            )
        }

        _countMap.value = mutableMapOf()

        _cartItems.value?.forEachIndexed { index, item ->
            _countMap.value?.set(index, item.count)
        }
    }


    fun incrementCount(position: Int){
        val map = _countMap.value ?: mutableMapOf()
        val current = map[position] ?: 1
        map[position] = current + 1
        _countMap.value = map
    }
    fun decrementCount(position: Int) {
        val map = _countMap.value ?: mutableMapOf()
        val current = map[position] ?: 1
        if (current > 1) {
            map[position] = current - 1
            _countMap.value = map
        }
    }

    fun toggleSelection(itemId: String) {
        val list = _cartItems.value?.map {
            if (it.id == itemId) it.copy(isSelected = !it.isSelected) else it
        }
        _cartItems.value = list
    }
}
