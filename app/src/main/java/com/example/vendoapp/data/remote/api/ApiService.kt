package com.example.vendoapp.data.remote.api

import com.example.vendoapp.data.model.ApiResponse
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordRequest
import com.example.vendoapp.data.model.auth.forgotpassword.ForgotPasswordResponse
import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.data.model.auth.otp.SendOtpRequest
import com.example.vendoapp.data.model.auth.otp.VerifyOtpRequest
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.data.model.auth.token.RefreshTokenRequest
import com.example.vendoapp.data.model.auth.token.RefreshTokenResponse
import com.example.vendoapp.data.model.cartModel.CartItem
import com.example.vendoapp.data.model.cartModel.CartItemRequest
import com.example.vendoapp.data.model.home.BannerResponse
import com.example.vendoapp.data.model.home.BrandResponse
import com.example.vendoapp.data.model.home.FavoriteResponse
import com.example.vendoapp.data.model.home.Product
import com.example.vendoapp.data.model.profile.myorderstestmodel.OrdersModel
import com.example.vendoapp.data.model.profile.ProfileModel
import com.example.vendoapp.data.model.profile.UpdateAvatarModelX
import com.example.vendoapp.data.model.profile.personalinformation.UpdateProfileRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): ApiResponse<RegisterResponse>

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResponse<LoginResponse>

    @POST("api/v1/auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse

    @POST("/api/v1/auth/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): ApiResponse<ForgotPasswordResponse>

    @POST("api/v1/otp/send")
    suspend fun sendOtp(@Body request: SendOtpRequest): ApiResponse<String>

    @POST("api/v1/otp/verify")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): ApiResponse<String>

    @GET("api/v1/user-profiles/{userId}")
    suspend fun getUserProfile(@Path("userId") userId: Int): ApiResponse<ProfileModel>

    @PUT("api/v1/user-profiles/{id}")
    suspend fun updateUserProfile(
        @Path("id") userId: Int,
        @Body request: UpdateProfileRequest
    ): ApiResponse<ProfileModel>

    @Multipart
    @PUT("/api/v1/user-profiles/{id}/avatar")
    suspend fun updateUserProfileImage(
        @Path("id") userId: Int,
        @Part avatar: MultipartBody.Part
    ) : ApiResponse<UpdateAvatarModelX>

    @GET("api/v1/banners")
    suspend fun getBanners(): Response<ApiResponse<List<BannerResponse>>>

    @GET("api/v1/brands/top")
    suspend fun getTopBrands(): Response<ApiResponse<List<BrandResponse>>>

    @GET("api/v1/products")
    suspend fun getAllProducts(): Response<ApiResponse<List<Product>>>

    @GET("api/v1/products/for-you")
    suspend fun getForYouProducts(): Response<ApiResponse<List<Product>>>

    @GET("api/v1/favorites")
    suspend fun getFavorites(): Response<ApiResponse<List<FavoriteResponse>>>

    @POST("api/v1/favorites/{productId}")
    suspend fun addFavorite(@Path("productId") productId: Int): Response<FavoriteResponse>

    @DELETE("api/v1/favorites/{productId}")
    suspend fun removeFavorite(@Path("productId") productId: Int): Response<Unit>

    @GET("api/v1/notifications/unread-count")
    suspend fun getUnreadNotificationCount(): Response<Int>

    @GET("api/v1/orders/customers/{customerId}")
    suspend fun getMyOrders(
        @Path("customerId") customerId: Int,
    ): ApiResponse<OrdersModel>

    @GET("api/v1/customers/{customerId}/carts/{cartId}/items")
    suspend fun getCartItems(
        @Path("customerId") customerId: Int,
        @Path("cartId") cartId: Int
    ): Response<ApiResponse<List<CartItem>>>

    @POST("api/v1/customers/{customerId}/carts/{cartId}/items/products/{productId}")
    suspend fun addToCart(
        @Path("customerId") customerId: Int,
        @Path("cartId") cartId: Int,
        @Path("productId") productId: Int,
        @Body request: CartItemRequest
    ): Response<ApiResponse<CartItem>>

    @PUT("api/v1/customers/{customerId}/carts/{cartId}/items/products/{productId}")
    suspend fun updateCartItem(
        @Path("customerId") customerId: Int,
        @Path("cartId") cartId: Int,
        @Path("productId") productId: Int,
        @Body request: CartItemRequest
    ): Response<ApiResponse<CartItem>>

    @DELETE("api/v1/customers/{customerId}/carts/{cartId}/items/products/{productId}")
    suspend fun deleteCartItem(
        @Path("customerId") customerId: Int,
        @Path("cartId") cartId: Int,
        @Path("productId") productId: Int,
    ): Response<Unit>

}