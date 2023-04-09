package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.ICollectionsRepository
import kotlinx.coroutines.flow.Flow

class DeleteMovieFromCollectionUseCase(private val collectionsRepository: ICollectionsRepository) {

    suspend fun execute(collectionId: String, movieId: String): Flow<Result<Boolean>> {
        return collectionsRepository.deleteMovieFromCollection(collectionId, movieId)
    }
}