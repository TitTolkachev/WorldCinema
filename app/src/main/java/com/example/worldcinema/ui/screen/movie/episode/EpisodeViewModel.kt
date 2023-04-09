package com.example.worldcinema.ui.screen.movie.episode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetEpisodeTimeUseCase
import com.example.worldcinema.domain.usecase.network.SaveEpisodeTimeUseCase
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeViewModel(
    movie: Movie,
    episode: MovieEpisode,
    val episodesCount: Int,
    val movieYears: String,
    private val getEpisodeTimeUseCase: GetEpisodeTimeUseCase,
    private val saveEpisodeTimeUseCase: SaveEpisodeTimeUseCase
) : ViewModel() {

    private val _movie: MutableLiveData<Movie> =
        MutableLiveData(movie)
    val movie: LiveData<Movie> = _movie

    private val _episode: MutableLiveData<MovieEpisode> =
        MutableLiveData(episode)
    val episode: LiveData<MovieEpisode> = _episode

    private val _episodeTime: MutableLiveData<Int> =
        MutableLiveData(0)
    val episodeTime: LiveData<Int> = _episodeTime

    init {
        loadEpisodeTime()
    }

    private fun loadEpisodeTime() {
        viewModelScope.launch(Dispatchers.IO) {
            getEpisodeTimeUseCase.execute(_episode.value!!.episodeId).collect { result ->
                result.onSuccess {
                    if (it + 5 >= _episode.value!!.runtime)
                        _episodeTime.postValue(0)
                    else
                        _episodeTime.postValue(it)
                }.onFailure {
                    // TODO(Показать ошибку)
                    Log.e("EPISODE TIME LOADING ERROR", it.message.toString())
                }
            }
        }
    }

    fun saveVideoPosition(contentPosition: Long) {
        var time = contentPosition.toInt() / 1000
        if (time > _episode.value!!.runtime)
            time = _episode.value!!.runtime

        viewModelScope.launch(Dispatchers.IO) {
            saveEpisodeTimeUseCase.execute(_episode.value!!.episodeId, time).collect { result ->
                result.onFailure {
                    // TODO(Показать ошибку)
                }
            }
        }
    }
}