package com.example.worldcinema.ui.screen.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.model.MovieFilter
import com.example.worldcinema.domain.usecase.network.GetCoverUseCase
import com.example.worldcinema.domain.usecase.network.GetMoviesUseCase
import com.example.worldcinema.ui.helper.MovieMapper
import com.example.worldcinema.ui.helper.MovieToPosterMapper
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MoviePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCoverUseCase: GetCoverUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _coverImage: MutableLiveData<String> =
        MutableLiveData()
    val coverImage: LiveData<String> = _coverImage

    private val _trendMovies: MutableLiveData<MutableList<Movie>> =
        MutableLiveData(mutableListOf())
    private val _newMovies: MutableLiveData<MutableList<Movie>> =
        MutableLiveData(mutableListOf())
    private val _recommendedMovies: MutableLiveData<MutableList<Movie>> =
        MutableLiveData(mutableListOf())
    private val _lastViewMovies: MutableLiveData<MutableList<Movie>> =
        MutableLiveData(mutableListOf())

    private val _trendMoviesPosters: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())
    private val _newMoviesPosters: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())
    private val _recommendedMoviesPosters: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())
    private val _lastViewMoviesPosters: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())

    val trendMovies: LiveData<MutableList<MoviePoster>> = _trendMoviesPosters
    val newMovies: LiveData<MutableList<MoviePoster>> = _newMoviesPosters
    val recommendedMovies: LiveData<MutableList<MoviePoster>> = _recommendedMoviesPosters
    val lastViewMovies: LiveData<MutableList<MoviePoster>> = _lastViewMoviesPosters

    init {
        loadCover()
        loadData()
    }

    private fun loadData() {
        loadTrendMovies()
        loadNewMovies()
        loadRecommendedMovies()
        loadLastViewMovies()
    }

    private fun loadCover() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoverUseCase.execute().collect { result ->
                result.onSuccess {
                    _coverImage.postValue(it.backgroundImage)
                }.onFailure {
                    // TODO(Обработать ошибку)
                    Log.e("GET COVER ERROR", it.message.toString())
                }
            }
        }
    }

    private fun loadTrendMovies() {
        loadMovies(MovieFilter.InTrend, _trendMovies, _trendMoviesPosters)
    }

    private fun loadNewMovies() {
        loadMovies(MovieFilter.New, _newMovies, _newMoviesPosters)
    }

    private fun loadRecommendedMovies() {
        loadMovies(MovieFilter.ForMe, _recommendedMovies, _recommendedMoviesPosters)
    }

    private fun loadLastViewMovies() {
        loadMovies(MovieFilter.LastView, _lastViewMovies, _lastViewMoviesPosters)
    }

    private fun loadMovies(
        filter: MovieFilter,
        movies: MutableLiveData<MutableList<Movie>>,
        posters: MutableLiveData<MutableList<MoviePoster>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.execute(filter).collect { result ->
                result.onSuccess {
                    val data = MovieMapper.mapMovies(it)
                    movies.postValue(data)
                    if (filter == MovieFilter.New || filter == MovieFilter.LastView)
                        posters.postValue(MovieToPosterMapper.mapMoviesImagesToPosters(data))
                    else
                        posters.postValue(MovieToPosterMapper.mapMovies(data))
                }.onFailure {
                    // TODO(Отобразить ошибку загрузки фильмов)
                    Log.e("MOVIES LOADING ERROR", it.message.toString())
                }
            }
        }
    }
}