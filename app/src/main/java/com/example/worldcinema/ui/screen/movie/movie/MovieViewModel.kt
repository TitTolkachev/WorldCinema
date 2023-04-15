package com.example.worldcinema.ui.screen.movie.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.R
import com.example.worldcinema.domain.usecase.network.GetEpisodesUseCase
import com.example.worldcinema.domain.usecase.network.GetMoviesInCollectionUseCase
import com.example.worldcinema.ui.helper.EpisodeMapper
import com.example.worldcinema.ui.helper.MovieMapper
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    movie: Movie?,
    private val movieId: String,
    private val collectionId: String,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val getMoviesInCollectionUseCase: GetMoviesInCollectionUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _movieImages: MutableLiveData<List<String>> =
        MutableLiveData(listOf())
    private val _movieEpisodes: MutableLiveData<MutableList<MovieEpisode>> =
        MutableLiveData(mutableListOf())
    val movieImages: LiveData<List<String>> = _movieImages
    val movieEpisodes: LiveData<MutableList<MovieEpisode>> = _movieEpisodes

    private val _movieAge: MutableLiveData<String> =
        MutableLiveData()
    val movieAge: LiveData<String> = _movieAge

    private val _isMovieDataLoading = MutableLiveData(movie == null)
    val isMovieDataLoading: LiveData<Boolean> = _isMovieDataLoading

    private val _isEpisodesDataLoading = MutableLiveData(true)
    val isEpisodesDataLoading: LiveData<Boolean> = _isEpisodesDataLoading

    init {
        if (movie == null) {
            loadMovieAndEpisodes()
        } else {
            _movie.value = movie!!
            _movieImages.value = _movie.value?.imageUrls
            _movieAge.value = _movie.value?.age
            loadEpisodes(movie.movieId)
        }
    }

    fun getMovieAgeColor(): Int {
        when (_movieAge.value) {
            "0+" -> return R.color.age_0_color
            "6+" -> return R.color.age_6_color
            "12+" -> return R.color.age_12_color
            "16+" -> return R.color.age_16_color
            "18+" -> return R.color.age_18_color
        }

        return R.color.age_18_color
    }

    fun getEpisode(episodeId: String): MovieEpisode? {
        if (_movieEpisodes.value != null) {

            for (e in _movieEpisodes.value!!) {
                if (e.episodeId == episodeId)
                    return e
            }
        }
        return null
    }

    fun getEpisodesCount(): Int {
        return _movieEpisodes.value?.count() ?: 0
    }

    fun getMovieYears(): String {
        var min = _movieEpisodes.value?.get(0)?.year ?: 3000
        var max = _movieEpisodes.value?.get(0)?.year ?: 0
        if (_movieEpisodes.value != null) {
            for (e in _movieEpisodes.value!!) {
                if (e.year > max)
                    max = e.year
                if (e.year < min)
                    min = e.year
            }
        }
        if (min == max)
            return min.toString()
        return "$min - $max"
    }

    private fun movieDataLoaded() {
        _isMovieDataLoading.postValue(false)
    }

    private fun episodesDataLoaded() {
        _isEpisodesDataLoading.postValue(false)
    }

    private fun loadEpisodes(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getEpisodesUseCase.execute(movieId).collect { result ->
                result.onSuccess {
                    _movieEpisodes.postValue(EpisodeMapper.mapEpisodes(it))
                    episodesDataLoaded()
                }.onFailure {
                    // TODO(Показать ошибку)
                    Log.e("EPISODE LOADING ERROR", it.message.toString())
                }
            }
        }
    }

    private fun loadMovieAndEpisodes() {

        viewModelScope.launch(Dispatchers.IO) {

            getMoviesInCollectionUseCase.execute(collectionId).collect{result->
                result.onSuccess { movieList ->
                    val loadedMovie = movieList.single { it.movieId == movieId }
                    _movie.postValue(MovieMapper.mapMovie(loadedMovie))
                    _movieImages.postValue(loadedMovie.imageUrls)
                    _movieAge.postValue(loadedMovie.age)
                    movieDataLoaded()
                }.onFailure {
                    // TODO(Показать ошибку!!!!)
                }
            }
        }

        loadEpisodes(movieId)
    }
}