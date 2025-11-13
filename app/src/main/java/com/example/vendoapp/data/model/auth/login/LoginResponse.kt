package com.example.vendoapp.data.model.auth.login

data class LoginResponse(

    val userId: Int,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
)