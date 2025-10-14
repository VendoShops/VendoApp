package com.example.vendoapp.data.model.auth.token

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val accessTokenExpiryDate: String,
    val refreshTokenExpiryDate: String
)
