package com.example.vendoapp.domain.repository

import com.example.vendoapp.data.model.ApiResponse
import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.data.model.cartModel.CartItemRequest
import com.example.vendoapp.data.model.cartModel.CartSummary
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CartRepository {

    override suspend fun getCartItems(customerId: Int): Resource<List<CartItem>> {
        return try {
            val response = apiService.getCartItems(customerId)
            if (response.isSuccessful) {
                Resource.Success(response.body().orEmpty())
            } else {
                Resource.Error("Network error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: ${e.localizedMessage ?: "I/O error"}")
        } catch (e: HttpException) {
            Resource.Error("HTTP exception: ${e.localizedMessage ?: "HTTP error"}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}")
        }
    }

    override suspend fun addToCart(
        customerId: Int,
        productId: Int,
        quantity: Int
    ): Resource<CartItem> {
        return try {
            val response = apiService.addProductToCart(customerId, productId, quantity)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) Resource.Success(data)
                else Resource.Error("Server returned empty item on add")
            } else {
                Resource.Error("Network error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: ${e.localizedMessage ?: "I/O error"}")
        } catch (e: HttpException) {
            Resource.Error("HTTP exception: ${e.localizedMessage ?: "HTTP error"}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}")
        }
    }

    override suspend fun updateQuantity(
        customerId: Int,
        cartItemId: Int,
        quantity: Int,
        cartItemRequest: CartItemRequest
    ): Resource<CartItem> {
        return try {
            val response = apiService.updateCartItemQuantity(customerId, cartItemId, quantity)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) Resource.Success(data)
                else Resource.Error("Server returned empty item on update")
            } else {
                Resource.Error("Network error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: ${e.localizedMessage ?: "I/O error"}")
        } catch (e: HttpException) {
            Resource.Error("HTTP exception: ${e.localizedMessage ?: "HTTP error"}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}")
        }
    }

    override suspend fun deleteCartItem(
        customerId: Int,
        cartItemId: Int,
        itemId: Int
    ): Resource<Unit> {
        return try {
            val response = apiService.deleteCartItem(customerId, cartItemId)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Network error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: ${e.localizedMessage ?: "I/O error"}")
        } catch (e: HttpException) {
            Resource.Error("HTTP exception: ${e.localizedMessage ?: "HTTP error"}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}")
        }
    }

    override suspend fun getCartSummary(customerId: Int, cartId: Int): Resource<CartSummary> {
        return try {
            val response = apiService.getCartSummary(customerId, cartId)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) Resource.Success(data)
                else Resource.Error("Server returned empty summary")
            } else {
                Resource.Error("Network error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: ${e.localizedMessage ?: "I/O error"}")
        } catch (e: HttpException) {
            Resource.Error("HTTP exception: ${e.localizedMessage ?: "HTTP error"}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}")
        }
    }

    override suspend fun toggleItemSelection(
        customerId: Int,
        cartId: Int,
        itemId: Int,
        selectionStatus: String
    ): Resource<CartItem> {
        return try {
            val response = apiService.toggleItemSelection(
                customerId = customerId,
                cartId = cartId,
                itemId = itemId,
                request = mapOf("selectionStatus" to selectionStatus)
            )
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) Resource.Success(data)
                else Resource.Error("Server returned empty item on toggle selection")
            } else {
                Resource.Error("Network error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network failure: ${e.localizedMessage ?: "I/O error"}")
        } catch (e: HttpException) {
            Resource.Error("HTTP exception: ${e.localizedMessage ?: "HTTP error"}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}")
        }
    }
}
