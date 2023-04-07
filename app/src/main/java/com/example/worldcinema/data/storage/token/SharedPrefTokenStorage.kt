package com.example.worldcinema.data.storage.token

import android.content.Context
import android.content.SharedPreferences
import com.example.worldcinema.data.storage.model.TokenModel

private const val APP_PREFERENCES = "preferences_settings"
private const val ACCESS_TOKEN_NAME = "access_token"
private const val REFRESH_TOKEN_NAME = "refresh_token"

class SharedPrefTokenStorage(context: Context) : ITokenStorage {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE
        )

    override fun getToken(): TokenModel? {

        val accessToken = preferences.getString(ACCESS_TOKEN_NAME, null)
        val refreshToken = preferences.getString(REFRESH_TOKEN_NAME, null)
        return if (accessToken != null && refreshToken != null)
            TokenModel(accessToken, refreshToken)
        else null
    }

    override fun saveToken(token: TokenModel) {
        preferences.edit().putString(ACCESS_TOKEN_NAME, token.accessToken).apply()
        preferences.edit().putString(REFRESH_TOKEN_NAME, token.refreshToken).apply()
    }
}