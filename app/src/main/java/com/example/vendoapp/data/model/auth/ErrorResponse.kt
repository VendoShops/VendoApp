package com.example.vendoapp.data.model.auth

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errorCode")
    val errorCode: Int?,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("path")
    val path: String?,

    @SerializedName("timestamp")
    val timestamp: String?,

    @SerializedName("message")
    val message: String
)