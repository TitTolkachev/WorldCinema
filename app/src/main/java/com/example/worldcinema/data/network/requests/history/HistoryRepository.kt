package com.example.worldcinema.data.network.requests.history

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.domain.i_repository.network.IHistoryRepository
import com.example.worldcinema.domain.model.EpisodeView
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepository(useCases: AuthNetworkUseCases) : IHistoryRepository {

    private val api = AuthNetwork.getHistoryApi(useCases)

    override suspend fun getHistory(): Flow<Result<List<EpisodeView>>> = flow {
        try {
            val data = api.getHistory()
            emit(Result.success(data.map { e ->
                EpisodeView(
                    e.episodeId,
                    e.movieId,
                    e.episodeName,
                    e.movieName,
                    e.preview,
                    e.filePath,
                    e.time
                )
            }))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}