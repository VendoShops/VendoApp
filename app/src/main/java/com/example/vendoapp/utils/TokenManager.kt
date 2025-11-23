package com.example.vendoapp.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveTokens(
        accessToken: String?,
        refreshToken: String?,
        accessExpiry: String?,
        refreshExpiry: String?
    ) {
        val editor = prefs.edit()
        accessToken?.let { editor.putString("access_token", it) }
        refreshToken?.let { editor.putString("refresh_token", it) }
        accessExpiry?.let { editor.putString("accessExpiry", it) }
        refreshExpiry?.let { editor.putString("refreshExpiry", it) }
        editor.apply()
    }

    fun getAccessToken(): String? = prefs.getString("access_token", null)
    fun getRefreshToken(): String? = prefs.getString("refresh_token", null)
    fun getAccessExpiry() = prefs.getString("accessExpiry", null)
    fun getRefreshExpiry() = prefs.getString("refreshExpiry", null)

    fun isTokenExpired(expiry: String?): Boolean {
        if (expiry.isNullOrEmpty()) return true
        return try {
            val cleanedExpiry = expiry.substringBeforeLast('.') + "Z"

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            val expiryDate = sdf.parse(cleanedExpiry)
            val now = Date()

            now.after(expiryDate)
        } catch (e: Exception) {
            true
        }
    }


    fun clearTokens() { prefs.edit().clear().apply() }

    fun saveUserCredentials(email: String, password: String) {
        prefs.edit().apply {
            putString("email", email)
            putString("password", password)
        }.apply()
    }

    fun isRememberMeChecked(): Boolean = prefs.getBoolean("remember_me", false)

    fun saveRememberMe(checked: Boolean) {
        prefs.edit().putBoolean("remember_me", checked).apply()
    }

    fun saveUserId(userId: Int) {
        prefs.edit().putInt("user_id", userId).apply()
    }

    fun getUserId(): Int = prefs.getInt("user_id", -1)

    fun saveLanguage(languageCode: String) {
        prefs.edit().putString("app_language", languageCode).apply()
    }

    fun getLanguage(): String = prefs.getString("app_language", "en") ?: "en"

    fun applyLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
