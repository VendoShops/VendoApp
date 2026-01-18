package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.data.model.cartModel.CartItemRequest
import com.example.vendoapp.data.model.cartModel.CartSummary
import com.example.vendoapp.utils.Resource

interface CartRepository {
    suspend fun getCartItems(customerId: Int): Resource<List<CartItem>>

    suspend fun addToCart(
        customerId: Int,
        productId: Int,
        quantity: Int
    ): Resource<CartItem>

    suspend fun updateQuantity(
        customerId: Int,
        cartItemId: Int,
        quantity: Int,
        cartItemRequest: CartItemRequest
    ): Resource<CartItem>

    suspend fun deleteCartItem(
        customerId: Int,
        cartItemId: Int,
        itemId: Int
    ): Resource<Unit>

    suspend fun getCartSummary(
        customerId: Int,
        cartId: Int
    ): Resource<CartSummary>

    suspend fun toggleItemSelection(
        customerId: Int,
        cartId: Int,
        itemId: Int,
        selectionStatus: String
    ): Resource<CartItem>
}


//interface CartRepository {
//
//    suspend fun getCartItems(customerId: Long): List<CartItem>
//    suspend fun addToCart(customerId: Long, productId: Long, quantity: Int = 1): CartItem
//    suspend fun updateQuantity(customerId: Long, cartItemId: Long, quantity: Int): CartItem
//    suspend fun removeItem(customerId: Int, cartId: Int, productId: Int)
//
////    suspend fun getCartItems(customerId: Int, cartId: Int): Resource<List<CartItem>>
//
////    suspend fun addToCart(
////        customerId: Int,
////        cartId: Int,
////        productId: Int,
////        request: CartItemRequest,
////    ): Resource<CartItem>
//
//    suspend fun updateCartItem(
//        customerId: Int,
//        cartId: Int,
//        productId: Int,
//        request: CartItemRequest,
//    ): Resource<CartItem>
//
////    suspend fun deleteCartItem(
////        customerId: Int,
////        cartId: Int,
////        productId: Int,
////    ): Resource<Unit>
//
//    suspend fun getCartSummary(
//        customerId: Int,
//        cartId: Int,
//    ): Resource<CartSummary>
//
//    suspend fun toggleItemSelection(
//        customerId: Int,
//        cartId: Int,
//        itemId: Int,
//        selectionStatus: String,
//    ): Resource<CartItem>
//}