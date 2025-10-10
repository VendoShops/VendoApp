package com.example.vendoapp.ui.login

import android.content.Context
import androidx.core.content.edit

object AuthPrefs {
    fun saveTokens(context: Context, accessToken: String, refreshToken: String) {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).edit {
            putString("access_token", accessToken)
                .putString("refresh_token", refreshToken)
        }
    }
}