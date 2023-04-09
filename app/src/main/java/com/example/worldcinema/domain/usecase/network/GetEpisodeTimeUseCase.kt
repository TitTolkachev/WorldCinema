package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IEpisodesRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeTimeUseCase(private val episodesRepository: IEpisodesRepository) {

    suspend fun execute(episodeId: String): Flow<Result<Int>> {
        return episodesRepository.getTime(episodeId)
    }
}