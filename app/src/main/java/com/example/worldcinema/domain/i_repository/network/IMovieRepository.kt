package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.Episode
import com.example.worldcinema.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getMovies(filter: String) : Flow<Result<List<Movie>>>

    suspend fun getEpisodes(movieId: String) : Flow<Result<List<Episode>>>

    suspend fun dislikeMovie(movieId: String) : Flow<Result<Boolean>>
}