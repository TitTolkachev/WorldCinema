package com.example.worldcinema.data.storage.shared_prefs.favourites_collection

interface IFavouritesCollectionStorage {

    fun getCollectionId(): String

    fun saveCollectionId(id: String)
}