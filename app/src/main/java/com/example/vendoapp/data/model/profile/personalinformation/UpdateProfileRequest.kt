package com.example.vendoapp.data.model.profile.personalinformation

data class UpdateProfileRequest(
    val avatarUrl: String?,
    val avatar: String?,
    val fullName: String,
    val termsAccepted: Boolean
)
