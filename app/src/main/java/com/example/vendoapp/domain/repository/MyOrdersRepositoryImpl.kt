package com.example.vendoapp.domain.repository

import com.example.vendoapp.utils.Resource
import com.example.vendoapp.data.model.profile.myorderstestmodel.OrdersModel
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.data.remote.network.safeApiCall
import javax.inject.Inject

class MyOrdersRepositoryImpl  @Inject constructor(
    private val apiService: ApiService
) : MyOrdersRepository{
    override suspend fun getMyOrders(customerId: Int): Resource<OrdersModel> {
        return safeApiCall { apiService.getMyOrders(customerId) }
    }

}