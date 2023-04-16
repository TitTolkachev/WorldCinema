package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.ICollectionIconRepository

class DeleteCollectionsIconsUseCase(private val repository: ICollectionIconRepository) {

    fun execute(): Result<Boolean> {
        return repository.deleteIcons()
    }
}