package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.EpisodeView
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository {

    suspend fun getHistory(): Flow<Result<List<EpisodeView>>>
}