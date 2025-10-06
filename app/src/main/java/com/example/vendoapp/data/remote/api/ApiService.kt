package com.example.vendoapp.data.remote.api

import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : RegisterResponse
}