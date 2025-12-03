package com.example.vendoapp.domain.usecase

import com.example.vendoapp.data.model.profile.myorderstestmodel.OrdersModel
import com.example.vendoapp.domain.repository.MyOrdersRepository
import com.example.vendoapp.utils.Resource
import javax.inject.Inject

class MyOrdersUseCase  @Inject constructor(
    private val repository: MyOrdersRepository
) {
    suspend operator fun invoke(customerId: Int): Resource<OrdersModel> {
        return repository.getMyOrders(customerId)
    }
}