package com.example.worldcinema.ui.screen.movie.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.AddMovieToCollectionUseCase
import com.example.worldcinema.domain.usecase.network.GetCollectionsUseCase
import com.example.worldcinema.domain.usecase.network.GetEpisodeTimeUseCase
import com.example.worldcinema.domain.usecase.network.SaveEpisodeTimeUseCase
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.ui.helper.CollectionMapper
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode
import com.example.worldcinema.ui.model.UsersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeViewModel(
    movie: Movie,
    episode: MovieEpisode,
    val episodesCount: Int,
    val movieYears: String,
    private val getEpisodeTimeUseCase: GetEpisodeTimeUseCase,
    private val saveEpisodeTimeUseCase: SaveEpisodeTimeUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase,
    private val addMovieToCollectionUseCase: AddMovieToCollectionUseCase
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

    private val _episodeCollections: MutableLiveData<MutableList<UsersCollection>> =
        MutableLiveData()
    val episodeCollections: LiveData<MutableList<UsersCollection>> = _episodeCollections

    init {
        loadEpisodeTime()
        loadCollections()
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

    fun addMovieToCollection(collectionId: String) {
        if (_movie.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                addMovieToCollectionUseCase.execute(collectionId, _movie.value!!.movieId)
                    .collect { result ->
                        result.onSuccess {
                            // TODO(Мб кинуть тост)
                        }.onFailure {
                            // TODO(Показать ошибку)
                        }
                    }
            }
        }
    }

    private fun loadCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            getCollectionsUseCase.execute().collect { result ->
                result.onSuccess {
                    _episodeCollections.postValue(CollectionMapper.mapCollections(it, listOf()))
                }
            }
        }
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
                }
            }
        }
    }
}