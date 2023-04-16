package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.ICollectionIconRepository
import com.example.worldcinema.domain.model.CollectionIcon

class UpdateCollectionIconUseCase(private val repository: ICollectionIconRepository) {

    fun execute(collectionIcon: CollectionIcon): Result<Boolean> {
        return repository.updateCollectionIcon(collectionIcon)
    }
}