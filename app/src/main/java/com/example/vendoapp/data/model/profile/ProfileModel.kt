package com.example.vendoapp.data.model.profile

data class ProfileModel(
    val id: Int,
    val userId: Int,
    val avatarUrl: String?,
    val fullName: String?,
    val termsAccepted: Boolean?
)
