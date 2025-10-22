package com.example.vendoapp.data.model.auth.otp

data class VerifyOtpRequest(
    val target: String,
    val channel: String,
    val code: String
)