package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.data.network.requests.movie.MovieRepository
import com.example.worldcinema.domain.model.Movie
import com.example.worldcinema.domain.usecase.model.MovieFilter
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(filter: MovieFilter): Flow<Result<List<Movie>>> {

        return when(filter) {
            MovieFilter.New -> movieRepository.getMovies("new")
            MovieFilter.InTrend -> movieRepository.getMovies("inTrend")
            MovieFilter.ForMe -> movieRepository.getMovies("forMe")
            MovieFilter.LastView -> movieRepository.getMovies("lastView")
            MovieFilter.Compilation -> movieRepository.getMovies("compilation")
        }
    }
}