package com.example.worldcinema.domain.i_repository.storage

interface IFavouritesCollectionStorageRepository {

    fun getCollectionId(): String

    fun saveCollectionId(id: String)
}