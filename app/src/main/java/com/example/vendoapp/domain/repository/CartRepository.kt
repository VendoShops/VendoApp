package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.data.model.cartModel.CartItemRequest
import com.example.vendoapp.data.model.cartModel.CartSummary
import com.example.vendoapp.utils.Resource

interface CartRepository {

    suspend fun getCartItems(customerId: Int, cartId: Int): Resource<List<CartItem>>

    suspend fun addToCart(
        customerId: Int,
        cartId: Int,
        productId: Int,
        request: CartItemRequest,
    ): Resource<CartItem>

    suspend fun updateCartItem(
        customerId: Int,
        cartId: Int,
        productId: Int,
        request: CartItemRequest,
    ): Resource<CartItem>

    suspend fun deleteCartItem(
        customerId: Int,
        cartId: Int,
        productId: Int,
    ): Resource<Unit>

    suspend fun getCartSummary(
        customerId: Int,
        cartId: Int,
    ): Resource<CartSummary>

    suspend fun toggleItemSelection(
        customerId: Int,
        cartId: Int,
        itemId: Int,
        selectionStatus: String,
    ): Resource<CartItem>
}