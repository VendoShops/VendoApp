package com.example.vendoapp.data.remote.network

import com.example.vendoapp.data.model.auth.token.RefreshTokenRequest
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.utils.TokenManager
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {

//    private val refreshRetrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("http://138.68.111.115:8080/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    private val refreshApi: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://138.68.111.115:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

//    private val apiService by lazy { refreshRetrofit.create(ApiService::class.java) }

    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = tokenManager.getAccessToken()
        val refreshToken = tokenManager.getRefreshToken()
        val accessExpiry = tokenManager.getAccessExpiry()
        val refreshExpiry = tokenManager.getRefreshExpiry()
        var originalRequest = chain.request()
        var mustRetry = false

        if (tokenManager.isTokenExpired(accessExpiry)) {

            if (tokenManager.isTokenExpired(refreshExpiry)) {
                tokenManager.clearTokens()
                accessToken = null
            } else if (!refreshToken.isNullOrEmpty()) {
                val newTokens = runBlocking {
                    try {
                        refreshApi.refreshToken(RefreshTokenRequest(refreshToken))
                    } catch (e: Exception) {
                        null
                    }
                }

                if (newTokens != null) {
                    tokenManager.saveTokens(
                        newTokens.accessToken,
                        newTokens.refreshToken,
                        newTokens.accessTokenExpiryDate,
                        newTokens.refreshTokenExpiryDate
                    )
                    accessToken = newTokens.accessToken
                    mustRetry = true
                } else {
                    tokenManager.clearTokens()
                    accessToken = null
                }
            }
        }

        if (!accessToken.isNullOrEmpty()) {
            originalRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }
        return chain.proceed(originalRequest)
    }

            /*
        var request = chain.request().newBuilder().apply {
            accessToken?.let { addHeader("Authorization", "Bearer $it") }
        }.build()

        var response = chain.proceed(request)

        if (response.code == 401) {
            response.close()

            if (!refreshToken.isNullOrEmpty()) {
                val newAccessToken = runBlocking {
                    try {
                        val refreshResponse = apiService.refreshToken(
                            RefreshTokenRequest(refreshToken)
                        )
                        val newToken = refreshResponse.accessToken
                        tokenManager.saveTokens(newToken, refreshToken)
                        newToken
                    } catch (e: Exception) {
                        null
                    }
                }

                if (newAccessToken != null) {
                    request = request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                    response = chain.proceed(request)
                } else {
                    tokenManager.clearTokens()
                }
            }
        }

         */


}