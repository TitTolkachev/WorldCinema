package com.example.worldcinema.data.storage.favourites_collection

interface IFavouritesCollectionStorage {

    fun getCollectionId(): String

    fun saveCollectionId(id: String)
}