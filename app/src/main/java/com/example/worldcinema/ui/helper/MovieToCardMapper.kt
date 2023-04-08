package com.example.worldcinema.ui.helper

import com.example.worldcinema.ui.model.Card
import com.example.worldcinema.ui.model.Movie

object MovieToCardMapper {

    fun mapMovie(m: Movie): Card {
        return Card(m.movieId, m.poster)
    }

    fun mapMovies(movies: List<Movie>): MutableList<Card> {
        val res = mutableListOf<Card>()
        for (m in movies)
            res.add(mapMovie(m))
        return res
    }
}