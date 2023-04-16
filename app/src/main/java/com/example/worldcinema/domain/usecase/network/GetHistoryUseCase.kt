package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IHistoryRepository
import com.example.worldcinema.domain.model.EpisodeView
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCase(private val repository: IHistoryRepository) {

    suspend fun execute(): Flow<Result<List<EpisodeView>>> {
        return repository.getHistory()
    }
}