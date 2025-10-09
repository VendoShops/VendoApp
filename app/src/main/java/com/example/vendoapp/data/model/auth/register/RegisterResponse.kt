package com.example.vendoapp.data.model.auth.register

data class RegisterResponse (
    val userId: Int,
    val email: String,
    val fullName: String,
    val accessToken: String,
    val refreshToken: String
)