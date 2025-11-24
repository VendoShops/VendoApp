package com.example.vendoapp.ui.profile.myorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vendoapp.data.model.myorderstestmodel.OrdersModel
import com.example.vendoapp.domain.usecase.MyOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val myOrdersUseCase: MyOrdersUseCase,
    private val tokenManager: TokenManager
) :ViewModel() {

    private val _orders = MutableLiveData<Resource<OrdersModel>>()
    val orders : LiveData<Resource<OrdersModel>> = _orders

    fun fetchOrders() {
        viewModelScope.launch {
            _orders.value = Resource.Loading()

            val customerId = tokenManager.getUserId()
            if (customerId == -1) {
                _orders.value = Resource.Error("User ID not found")
                return@launch
            }

            val response = myOrdersUseCase(customerId)
            _orders.value = response
        }

    }
}