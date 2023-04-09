package com.example.worldcinema.data.network.requests.episodes

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.data.network.dto.EpisodeTimeDto
import com.example.worldcinema.domain.i_repository.network.IEpisodesRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EpisodesRepository(useCases: AuthNetworkUseCases) : IEpisodesRepository {

    private val api = AuthNetwork.getEpisodesApi(useCases)

    override suspend fun getTime(episodeId: String): Flow<Result<Int>> = flow {
        try {
            val data = api.getTime(episodeId)
            emit(Result.success(data.timeInSeconds ?: 0))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveTime(episodeId: String, time: Int): Flow<Result<Boolean>> = flow {
        try {
            api.saveTime(episodeId, EpisodeTimeDto(time))
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}