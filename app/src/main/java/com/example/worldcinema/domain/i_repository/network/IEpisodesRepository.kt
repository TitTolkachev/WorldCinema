package com.example.worldcinema.domain.i_repository.network

import kotlinx.coroutines.flow.Flow

interface IEpisodesRepository {

    suspend fun getTime(episodeId: String): Flow<Result<Int>>

    suspend fun saveTime(episodeId: String, time: Int): Flow<Result<Boolean>>
}