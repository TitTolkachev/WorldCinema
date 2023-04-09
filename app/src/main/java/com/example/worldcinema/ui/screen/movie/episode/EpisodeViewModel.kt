package com.example.worldcinema.ui.screen.movie.episode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode

class EpisodeViewModel(
    movie: Movie,
    episode: MovieEpisode,
    val episodesCount: Int,
    val movieYears: String
) : ViewModel() {

    private val _movie: MutableLiveData<Movie> =
        MutableLiveData(movie)
    val movie: LiveData<Movie> = _movie

    private val _episode: MutableLiveData<MovieEpisode> =
        MutableLiveData(episode)
    val episode: LiveData<MovieEpisode> = _episode

    init {

    }

    fun saveVideoPosition(contentPosition: Long, contentDuration: Long) {
        Log.d("Position", contentPosition.toString())
        Log.d("Duration", contentDuration.toString())
    }
}