package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.ICollectionIconRepository
import com.example.worldcinema.domain.model.CollectionIcon

class GetCollectionsIconsUseCase (private val repository: ICollectionIconRepository) {

    fun execute(): Result<List<CollectionIcon>> {
        return repository.getIcons()
    }
}