package com.example.vendoapp.data.model.auth.otp

data class SendOtpRequest(
    val target: String,
    val channel: String,
    val code: String? = null
)