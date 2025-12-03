package com.example.vendoapp.data.model.profile

import com.google.gson.annotations.SerializedName

data class UpdateAvatarModelX(
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("termsAccepted")
    val termsAccepted: Boolean,
    @SerializedName("userId")
    val userId: Int
)