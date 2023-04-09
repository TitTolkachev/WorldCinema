package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IEpisodesRepository
import kotlinx.coroutines.flow.Flow

class SaveEpisodeTimeUseCase(private val episodesRepository: IEpisodesRepository) {

    suspend fun execute(episodeId: String, time: Int): Flow<Result<Boolean>> {
        return episodesRepository.saveTime(episodeId, time)
    }
}