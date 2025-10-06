package com.example.vendoapp.data.model.auth

data class ErrorResponse (
    val errorCode: Int,
    val success: Boolean,
    val path: String,
    val timestamp: String,
    val message: String
)
