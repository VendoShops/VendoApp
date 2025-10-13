package com.example.vendoapp.data.remote.network

import com.example.vendoapp.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = tokenManager.getAccessToken()
        val request = chain.request().newBuilder().apply {
            accessToken?.let { addHeader("Authorization", "Bearer $accessToken") }
        }.build()
        return chain.proceed(request)
    }
}
