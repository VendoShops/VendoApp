package com.example.vendoapp.data.model.auth.forgotpassword

data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String,
    val errorCode: Int? = null,
    val path: String? = null,
    val timestamp: String? = null
)