package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.ICollectionsRepository
import kotlinx.coroutines.flow.Flow

class CreateCollectionUseCase(private val collectionsRepository: ICollectionsRepository) {

    suspend fun execute(name: String): Flow<Result<String>> {
        return collectionsRepository.createCollection(name)
    }
}