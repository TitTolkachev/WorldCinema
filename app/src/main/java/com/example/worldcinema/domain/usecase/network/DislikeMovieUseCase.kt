package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.data.network.requests.movie.MovieRepository
import kotlinx.coroutines.flow.Flow

class DislikeMovieUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(movieId: String): Flow<Result<Boolean>> {
        return movieRepository.dislikeMovie(movieId)
    }
}