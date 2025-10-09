package com.example.vendoapp.data.remote.network

import android.util.Log
import com.example.vendoapp.data.model.auth.ErrorResponse
import com.example.vendoapp.utils.Resource
import com.google.gson.Gson
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
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                errorResponse.message
            } catch (ex: Exception) {
                "Server Error: ${e.code()}"
            }
            Resource.Error(errorMessage)
        } catch (e: Exception) {
            Log.d("safeApiCall", "Exception: ${e.message}")
            Resource.Error(e.localizedMessage ?: "Something went wrong")
        }
    }
}