package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.ICollectionsRepository
import kotlinx.coroutines.flow.Flow

class DeleteCollectionUseCase (private val collectionsRepository: ICollectionsRepository) {

    suspend fun execute(collectionId: String): Flow<Result<Boolean>> {
        return collectionsRepository.deleteCollection(collectionId)
    }
}