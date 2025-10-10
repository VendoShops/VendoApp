package com.example.vendoapp.domain.repository

import android.util.Log
import com.example.vendoapp.data.model.auth.ErrorResponse
import com.example.vendoapp.data.model.auth.login.LoginRequest
import com.example.vendoapp.data.model.auth.login.LoginResponse
import com.example.vendoapp.data.model.auth.register.RegisterRequest
import com.example.vendoapp.data.model.auth.register.RegisterResponse
import com.example.vendoapp.data.remote.api.ApiService
import com.example.vendoapp.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(registerRequest)
                Log.d("AuthRepository", "Register success: $response")
                Resource.Success(response)
            } catch (e: IOException) {
                Log.e("AuthRepository", "Register IOException: ${e.message}")
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
                Log.e("AuthRepository", "Register HttpException: $errorMessage")
                Resource.Error(errorMessage)
            } catch (e: Exception) {
                Log.e("AuthRepository", "Register Exception: ${e.message}")
                Resource.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }

    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(loginRequest)
                Log.d("AuthRepository", "Login success: $response")
                Resource.Success(response)
            } catch (e: IOException) {
                Log.e("AuthRepository", "Login IOException: ${e.message}")
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
                Log.e("AuthRepository", "Login HttpException: $errorMessage")
                Resource.Error(errorMessage)
            } catch (e: Exception) {
                Log.e("AuthRepository", "Login Exception: ${e.message}")
                Resource.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }
}