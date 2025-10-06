package com.example.vendoapp.utils

sealed class Resource<T> (
    val data: T? = null,
    open val message: String? = null
) {
    class Idle<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(override val message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}