package com.example.worldcinema.data.storage.shared_prefs.favourites_collection

import com.example.worldcinema.domain.i_repository.storage.IFavouritesCollectionStorageRepository

class FavouritesCollectionStorageRepository(private val storage: IFavouritesCollectionStorage) :
    IFavouritesCollectionStorageRepository {

    override fun getCollectionId(): String {
        return storage.getCollectionId()
    }

    override fun saveCollectionId(id: String) {
        storage.saveCollectionId(id)
    }
}