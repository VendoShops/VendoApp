package com.example.vendoapp.data.model.auth.register

data class RegisterRequest (
    val fullName: String,
    val email: String,
    val password: String
    )