package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.Movie
import com.example.worldcinema.ui.model.MovieInCollection

object MovieMapper {

    fun mapMovie(m: Movie): com.example.worldcinema.ui.model.Movie {
        return com.example.worldcinema.ui.model.Movie(
            m.movieId,
            m.name,
            m.description,
            m.age,
            ChatInfoMapper.mapChatInfo(m.chatInfo),
            m.imageUrls,
            m.poster,
            TagMapper.mapTags(m.tags)
        )
    }

    fun mapMovies(movies: List<Movie>): MutableList<com.example.worldcinema.ui.model.Movie> {
        val res = mutableListOf<com.example.worldcinema.ui.model.Movie>()
        for (m in movies)
            res.add(mapMovie(m))
        return res
    }

    fun mapMovieToCollectionMovie(m: Movie): MovieInCollection {
        return MovieInCollection(
            m.movieId,
            m.name,
            m.description,
            m.poster
        )
    }

    fun mapMoviesToCollectionMovies(movies: List<Movie>): MutableList<MovieInCollection> {
        val res = mutableListOf<MovieInCollection>()
        for (m in movies)
            res.add(mapMovieToCollectionMovie(m))
        return res
    }
}