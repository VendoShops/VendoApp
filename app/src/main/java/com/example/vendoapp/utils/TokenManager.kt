package com.example.vendoapp.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveTokens(accessToken: String?, refreshToken: String?) {
        val editor = prefs.edit()
        accessToken?.let { editor.putString("access_token", it) }
        refreshToken?.let { editor.putString("refresh_token", it) }
        editor.apply()
    }

    fun getAccessToken(): String? = prefs.getString("access_token", null)
    fun getRefreshToken(): String? = prefs.getString("refresh_token", null)
    fun clearTokens() { prefs.edit().clear().apply() }
}