package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.ICollectionsRepository
import com.example.worldcinema.domain.model.CollectionListItem
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase(private val collectionsRepository: ICollectionsRepository) {

    suspend fun execute(): Flow<Result<List<CollectionListItem>>> {
        return collectionsRepository.getCollections()
    }
}