package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.ICollectionsRepository
import com.example.worldcinema.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class GetMoviesInCollectionUseCase(private val collectionsRepository: ICollectionsRepository) {

    suspend fun execute(collectionId: String): Flow<Result<List<Movie>>> {
        return collectionsRepository.getMoviesInCollection(collectionId)
    }
}