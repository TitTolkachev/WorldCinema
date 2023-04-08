package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.data.network.requests.movie.MovieRepository
import com.example.worldcinema.domain.model.Episode
import kotlinx.coroutines.flow.Flow

class GetEpisodesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(movieId: String): Flow<Result<List<Episode>>> {
        return movieRepository.getEpisodes(movieId)
    }
}