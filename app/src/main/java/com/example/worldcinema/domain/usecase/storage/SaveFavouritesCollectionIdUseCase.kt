package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.IFavouritesCollectionStorageRepository

class SaveFavouritesCollectionIdUseCase(private val repository: IFavouritesCollectionStorageRepository) {

    fun execute(collectionId: String) {
        repository.saveCollectionId(collectionId)
    }
}