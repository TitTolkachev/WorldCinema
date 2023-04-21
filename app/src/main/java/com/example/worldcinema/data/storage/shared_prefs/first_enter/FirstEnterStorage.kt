package com.example.worldcinema.data.storage.shared_prefs.first_enter

import android.content.Context
import android.content.SharedPreferences

private const val APP_PREFERENCES = "preferences_settings"
private const val FIRST_ENTER = "first_enter"

class FirstEnterStorage(context: Context) : IFirstEnterStorage {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE
        )

    override fun getFirstEnterInfo(): Boolean {
        return preferences.getBoolean(FIRST_ENTER, true)
    }

    override fun setFirstEnterInfo(value: Boolean) {
        preferences.edit().putBoolean(FIRST_ENTER, value).apply()
    }
}