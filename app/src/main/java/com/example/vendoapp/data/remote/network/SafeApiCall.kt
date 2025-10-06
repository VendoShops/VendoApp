package com.example.vendoapp.data.remote.network

import android.util.Log
import com.example.vendoapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            Log.d("safeApiCall", "Success response=$response")
            Resource.Success(response)
        } catch (e: IOException) {
            Log.d("safeApiCall", "IOException: ${e.message}")
            Resource.Error("No Internet")
        } catch (e: HttpException) {
            Log.d("safeApiCall", "HttpException: ${e.code()}")
            Resource.Error("Server Error: ${e.code()}")
        } catch (e: Exception) {
            Log.d("safeApiCall", "Exception: ${e.message}")
            Resource.Error(e.localizedMessage ?: "Something went wrong")
        }
    }
}
