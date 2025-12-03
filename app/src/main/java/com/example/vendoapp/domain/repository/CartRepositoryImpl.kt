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

    // -------------------------
    // 1. Get cart items
    // -------------------------
    override suspend fun getCartItems(customerId: Int, cartId: Int): Resource<List<CartItem>> {
        return try {
            val response = apiService.getCartItems(customerId, cartId)
            if (response.isSuccessful) {
                val body: ApiResponse<List<CartItem>>? = response.body()
                val data: List<CartItem> = body?.data ?: emptyList()
                Resource.Success(data)
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

    // -------------------------
    // 2. Add to cart
    // -------------------------
    override suspend fun addToCart(
        customerId: Int,
        cartId: Int,
        productId: Int,
        request: CartItemRequest
    ): Resource<CartItem> {
        return try {
            val response = apiService.addToCart(customerId, cartId, productId, request)
            if (response.isSuccessful) {
                val body: ApiResponse<CartItem>? = response.body()
                val data: CartItem? = body?.data
                if (data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error("Server returned empty item on add")
                }
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

    // -------------------------
    // 3. Update cart item (quantity, color, size)
    // -------------------------
    override suspend fun updateCartItem(
        customerId: Int,
        cartId: Int,
        productId: Int,
        request: CartItemRequest
    ): Resource<CartItem> {
        return try {
            val response = apiService.updateCartItem(customerId, cartId, productId, request)
            if (response.isSuccessful) {
                val body: ApiResponse<CartItem>? = response.body()
                val data: CartItem? = body?.data
                if (data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error("Server returned empty item on update")
                }
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

    // -------------------------
    // 4. Delete cart item
    // -------------------------
    override suspend fun deleteCartItem(
        customerId: Int,
        cartId: Int,
        productId: Int
    ): Resource<Unit> {
        return try {
            val response = apiService.deleteCartItem(customerId, cartId, productId)
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

    // -------------------------
    // 5. Get cart summary (YENİ)
    // -------------------------
    override suspend fun getCartSummary(
        customerId: Int,
        cartId: Int
    ): Resource<CartSummary> {
        return try {
            val response = apiService.getCartSummary(customerId, cartId)
            if (response.isSuccessful) {
                val body: ApiResponse<CartSummary>? = response.body()
                val data: CartSummary? = body?.data
                if (data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error("Server returned empty summary")
                }
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

    // -------------------------
    // 6. Toggle item selection (YENİ)
    // -------------------------
    override suspend fun toggleItemSelection(
        customerId: Int,
        cartId: Int,
        itemId: Int,
        selectionStatus: String
    ): Resource<CartItem> {
        return try {
            val response = apiService.toggleItemSelection(
                customerId,
                cartId,
                itemId,
                mapOf("selectionStatus" to selectionStatus)
            )
            if (response.isSuccessful) {
                val body: ApiResponse<CartItem>? = response.body()
                val data: CartItem? = body?.data
                if (data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error("Server returned empty item on toggle selection")
                }
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