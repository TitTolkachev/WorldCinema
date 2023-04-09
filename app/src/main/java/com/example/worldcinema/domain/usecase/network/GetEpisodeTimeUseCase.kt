package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.data.network.requests.episodes.EpisodesRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeTimeUseCase(private val episodesRepository: EpisodesRepository) {

    suspend fun execute(episodeId: String): Flow<Result<Int>> {
        return episodesRepository.getTime(episodeId)
    }
}