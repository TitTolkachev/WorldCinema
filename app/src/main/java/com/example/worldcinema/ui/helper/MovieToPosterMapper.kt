package com.example.worldcinema.ui.helper

import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MoviePoster


object MovieToPosterMapper {

    fun mapMovie(m: Movie): MoviePoster {
        return MoviePoster(m.movieId, m.poster)
    }

    fun mapMovies(movies: List<Movie>): MutableList<MoviePoster> {
        val res = mutableListOf<MoviePoster>()
        for (m in movies)
            res.add(mapMovie(m))
        return res
    }

    fun mapMovieImageToPoster(m: Movie): MoviePoster {
        return MoviePoster(m.movieId, if (m.imageUrls.isEmpty()) m.poster else m.imageUrls[0])
    }

    fun mapMoviesImagesToPosters(movies: List<Movie>): MutableList<MoviePoster> {
        val res = mutableListOf<MoviePoster>()
        for (m in movies)
            res.add(mapMovieImageToPoster(m))
        return res
    }
}