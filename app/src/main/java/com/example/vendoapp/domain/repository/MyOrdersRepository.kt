package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.profile.myorderstestmodel.OrdersModel
import com.example.vendoapp.utils.Resource

interface MyOrdersRepository{
    suspend fun getMyOrders(customerId: Int) : Resource<OrdersModel>
}