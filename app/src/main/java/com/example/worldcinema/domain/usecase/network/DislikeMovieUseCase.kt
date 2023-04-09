package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IMovieRepository
import kotlinx.coroutines.flow.Flow

class DislikeMovieUseCase(private val movieRepository: IMovieRepository) {

    suspend fun execute(movieId: String): Flow<Result<Boolean>> {
        return movieRepository.dislikeMovie(movieId)
    }
}