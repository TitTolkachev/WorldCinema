package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.data.network.requests.episodes.EpisodesRepository
import kotlinx.coroutines.flow.Flow

class SaveEpisodeTimeUseCase(private val episodesRepository: EpisodesRepository) {

    suspend fun execute(episodeId: String, time: Int): Flow<Result<Boolean>> {
        return episodesRepository.saveTime(episodeId, time)
    }
}