package com.example.vendoapp.data.remote.network

import com.example.vendoapp.data.model.auth.token.RefreshTokenRequest
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.utils.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {

    private val refreshRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://138.68.111.115:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy { refreshRetrofit.create(ApiService::class.java) }

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = tokenManager.getAccessToken()
        var request = chain.request().newBuilder().apply {
            accessToken?.let { addHeader("Authorization", "Bearer $it") }
        }.build()

        var response = chain.proceed(request)

        if (response.code == 401) {
            response.close()
            val refreshToken = tokenManager.getRefreshToken()

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
        return response
    }
}