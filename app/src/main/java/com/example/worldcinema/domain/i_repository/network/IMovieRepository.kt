package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getMovies(filter: String) : Flow<Result<List<Movie>>>
}