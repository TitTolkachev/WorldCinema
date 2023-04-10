package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.IFavouritesCollectionStorageRepository

class GetFavouritesCollectionIdUseCase(private val repository: IFavouritesCollectionStorageRepository) {

    fun execute(): String {
        return repository.getCollectionId()
    }
}