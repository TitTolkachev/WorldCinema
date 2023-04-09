package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.CollectionListItem
import com.example.worldcinema.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface ICollectionsRepository {

    suspend fun getCollections(): Flow<Result<List<CollectionListItem>>>

    suspend fun createCollection(name: String): Flow<Result<Boolean>>

    suspend fun deleteCollection(collectionId: String): Flow<Result<Boolean>>

    suspend fun getMoviesInCollection(collectionId: String): Flow<Result<List<Movie>>>

    suspend fun addMovieToCollection(collectionId: String, movieId: String): Flow<Result<Boolean>>

    suspend fun deleteMovieFromCollection(
        collectionId: String,
        movieId: String
    ): Flow<Result<Boolean>>
}