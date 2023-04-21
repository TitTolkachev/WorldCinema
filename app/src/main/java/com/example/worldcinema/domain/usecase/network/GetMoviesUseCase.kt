package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IMovieRepository
import com.example.worldcinema.domain.model.Movie
import com.example.worldcinema.domain.usecase.model.MovieFilter
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(private val movieRepository: IMovieRepository) {

    suspend fun execute(filter: MovieFilter): Flow<Result<List<Movie>>> {

        return when(filter) {
            MovieFilter.NEW -> movieRepository.getMovies("new")
            MovieFilter.IN_TREND -> movieRepository.getMovies("inTrend")
            MovieFilter.FOR_ME -> movieRepository.getMovies("forMe")
            MovieFilter.LAST_VIEW -> movieRepository.getMovies("lastView")
            MovieFilter.COMPILATION -> movieRepository.getMovies("compilation")
        }
    }
}