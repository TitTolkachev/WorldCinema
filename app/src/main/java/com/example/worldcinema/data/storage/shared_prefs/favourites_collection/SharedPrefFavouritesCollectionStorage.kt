package com.example.worldcinema.data.storage.shared_prefs.favourites_collection

import android.content.Context
import android.content.SharedPreferences

private const val APP_PREFERENCES = "preferences_settings"
private const val FAVOURITES_COLLECTION_ID = "favourites_collection_id"

class SharedPrefFavouritesCollectionStorage(context: Context) : IFavouritesCollectionStorage {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE
        )

    override fun getCollectionId(): String {
        val id = preferences.getString(FAVOURITES_COLLECTION_ID, "")
        return id ?: ""
    }

    override fun saveCollectionId(id: String) {
        preferences.edit().putString(FAVOURITES_COLLECTION_ID, id).apply()
    }
}