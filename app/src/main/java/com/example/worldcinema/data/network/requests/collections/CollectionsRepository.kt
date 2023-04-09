package com.example.worldcinema.data.network.requests.collections

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.data.network.dto.*
import com.example.worldcinema.domain.i_repository.network.ICollectionsRepository
import com.example.worldcinema.domain.model.Chat
import com.example.worldcinema.domain.model.CollectionListItem
import com.example.worldcinema.domain.model.Movie
import com.example.worldcinema.domain.model.Tag
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CollectionsRepository(useCases: AuthNetworkUseCases) : ICollectionsRepository {

    private val api = AuthNetwork.getCollectionsApi(useCases)

    override suspend fun getCollections(): Flow<Result<List<CollectionListItem>>> = flow {
        try {
            val data = api.getCollections()
            emit(Result.success(mapCollectionListItems(data)))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun createCollection(collectionId: String): Flow<Result<Boolean>> = flow {
        try {
            api.createCollection(CollectionFormDto(collectionId))
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteCollection(collectionId: String): Flow<Result<Boolean>> = flow {
        try {
            api.deleteCollection(collectionId)
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMoviesInCollection(collectionId: String): Flow<Result<List<Movie>>> =
        flow {
            try {
                val data = api.getMoviesInCollection(collectionId)
                val movies = mutableListOf<Movie>()
                for (m in data) {
                    movies.add(mapMovie(m))
                }
                emit(Result.success(movies))
            } catch (e: Exception) {
                emit(Result.failure(Throwable(e)))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun addMovieToCollection(
        collectionId: String,
        movieId: String
    ): Flow<Result<Boolean>> = flow {
        try {
            api.addMovieToCollection(collectionId, MovieValueDto(movieId))
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteMovieFromCollection(
        collectionId: String,
        movieId: String
    ): Flow<Result<Boolean>> = flow {
        try {
            api.deleteMovieFromCollection(collectionId, movieId)
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun mapCollectionListItems(collections: List<CollectionListItemDto>): List<CollectionListItem> {
        val res = mutableListOf<CollectionListItem>()
        for (c in collections)
            res.add(mapCollectionListItem(c))
        return res.toList()
    }

    private fun mapCollectionListItem(c: CollectionListItemDto): CollectionListItem {
        return CollectionListItem(c.collectionId, c.name)
    }

    private fun mapChat(c: ChatDto): Chat {
        return Chat(c.chatId, c.chatName)
    }

    private fun mapTag(t: TagDto): Tag {
        return Tag(t.tagId, t.tagName, t.categoryName)
    }

    private fun mapMovie(m: MovieDto): Movie {

        val tags = mutableListOf<Tag>()
        for (t in m.tags) {
            tags.add(mapTag(t))
        }

        return Movie(
            m.movieId,
            m.name,
            m.description,
            m.age,
            mapChat(m.chatInfo),
            m.imageUrls,
            m.poster,
            tags.toList()
        )
    }
}