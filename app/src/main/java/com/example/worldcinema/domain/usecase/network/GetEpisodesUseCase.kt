package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IMovieRepository
import com.example.worldcinema.domain.model.Episode
import kotlinx.coroutines.flow.Flow

class GetEpisodesUseCase(private val movieRepository: IMovieRepository) {

    suspend fun execute(movieId: String): Flow<Result<List<Episode>>> {
        return movieRepository.getEpisodes(movieId)
    }
}