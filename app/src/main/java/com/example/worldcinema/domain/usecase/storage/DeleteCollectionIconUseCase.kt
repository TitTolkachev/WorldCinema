package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.ICollectionIconRepository

class DeleteCollectionIconUseCase(private val repository: ICollectionIconRepository) {

    fun execute(collectionId: String): Result<Boolean> {
        return repository.deleteIcon(collectionId)
    }
}