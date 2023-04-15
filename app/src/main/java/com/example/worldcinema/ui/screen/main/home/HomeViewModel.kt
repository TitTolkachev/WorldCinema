package com.example.worldcinema.ui.screen.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.model.MovieFilter
import com.example.worldcinema.domain.usecase.network.CreateCollectionUseCase
import com.example.worldcinema.domain.usecase.network.GetCollectionsUseCase
import com.example.worldcinema.domain.usecase.network.GetCoverUseCase
import com.example.worldcinema.domain.usecase.network.GetMoviesUseCase
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.SaveFavouritesCollectionIdUseCase
import com.example.worldcinema.ui.helper.MovieMapper
import com.example.worldcinema.ui.helper.MovieToPosterMapper
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MoviePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val favouritesCollectionName: String,
    private val getCoverUseCase: GetCoverUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase,
    private val saveFavouritesCollectionIdUseCase: SaveFavouritesCollectionIdUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase
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

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataLoadedCounter = 0
    private val requestsCount = 5

    init {
        _isLoading.value = true
        dataLoadedCounter = 0
        checkIsFirstEnter()
        loadCover()
        loadData()
    }

    private fun checkIsFirstEnter() {
        if (getFavouritesCollectionIdUseCase.execute().isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                getCollectionsUseCase.execute().collect { result ->
                    result.onSuccess { collectionListItems ->
                        val favouritesCollection =
                            collectionListItems.singleOrNull { el -> el.name == favouritesCollectionName }
                        if (favouritesCollection == null) {
                            createCollectionUseCase.execute(favouritesCollectionName)
                                .collect { result ->
                                    result.onSuccess {
                                        saveFavouritesCollectionIdUseCase.execute(it)
                                    }.onFailure {
                                        // TODO(Показать ошибку)
                                    }
                                }
                        } else {
                            saveFavouritesCollectionIdUseCase.execute(favouritesCollection.collectionId)
                        }
                    }.onFailure {
                        // TODO(Показать ошибку)
                    }
                }
            }
        }
    }

    private fun loadData() {
        loadMovies(MovieFilter.InTrend, _trendMovies, _trendMoviesPosters)
        loadMovies(MovieFilter.New, _newMovies, _newMoviesPosters)
        loadMovies(MovieFilter.ForMe, _recommendedMovies, _recommendedMoviesPosters)
        loadMovies(MovieFilter.LastView, _lastViewMovies, _lastViewMoviesPosters)
    }

    private fun dataLoaded() {
        if (++dataLoadedCounter == requestsCount)
            _isLoading.postValue(false)
    }

    private fun loadCover() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoverUseCase.execute().collect { result ->
                result.onSuccess {
                    dataLoaded()
                    _coverImage.postValue(it.backgroundImage)
                }.onFailure {
                    // TODO(Обработать ошибку)
                    Log.e("GET COVER ERROR", it.message.toString())
                }
            }
        }
    }

    private fun loadMovies(
        filter: MovieFilter,
        movies: MutableLiveData<MutableList<Movie>>,
        posters: MutableLiveData<MutableList<MoviePoster>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.execute(filter).collect { result ->
                result.onSuccess {
                    dataLoaded()
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

    fun getMovie(movieId: String): Movie? {
        for (m in _trendMovies.value!!) {
            if (m.movieId == movieId)
                return m
        }
        for (m in _newMovies.value!!) {
            if (m.movieId == movieId)
                return m
        }
        for (m in _recommendedMovies.value!!) {
            if (m.movieId == movieId)
                return m
        }
        for (m in _lastViewMovies.value!!) {
            if (m.movieId == movieId)
                return m
        }
        return null
    }
}