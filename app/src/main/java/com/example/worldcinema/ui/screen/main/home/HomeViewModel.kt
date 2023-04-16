package com.example.worldcinema.ui.screen.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.EpisodeView
import com.example.worldcinema.domain.usecase.model.MovieFilter
import com.example.worldcinema.domain.usecase.network.*
import com.example.worldcinema.domain.usecase.storage.DeleteCollectionsIconsUseCase
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.SaveFavouritesCollectionIdUseCase
import com.example.worldcinema.ui.helper.EpisodeMapper
import com.example.worldcinema.ui.helper.MovieMapper
import com.example.worldcinema.ui.helper.MovieToPosterMapper
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode
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
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val deleteCollectionsIconsUseCase: DeleteCollectionsIconsUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase
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

    private val _lastViewMovie = MutableLiveData<Movie>()
    val lastViewMovie: LiveData<Movie> = _lastViewMovie
    private val _lastViewEpisode = MutableLiveData<MovieEpisode>()
    val lastViewEpisode: LiveData<MovieEpisode> = _lastViewEpisode
    private val _lastViewMovieEpisodesCount = MutableLiveData(0)
    val lastViewMovieEpisodesCount: LiveData<Int> = _lastViewMovieEpisodesCount
    private val _lastViewMovieYears = MutableLiveData("")
    val lastViewMovieYears: LiveData<String> = _lastViewMovieYears

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataLoadedCounter = 0
    private val requestsCount = 6

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

                deleteCollectionsIconsUseCase.execute()

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
        viewModelScope.launch(Dispatchers.IO) {
            loadMovies(MovieFilter.LastView, _lastViewMovies, _lastViewMoviesPosters)
            loadMovies(MovieFilter.InTrend, _trendMovies, _trendMoviesPosters)
            loadMovies(MovieFilter.New, _newMovies, _newMoviesPosters)
            loadMovies(MovieFilter.ForMe, _recommendedMovies, _recommendedMoviesPosters)
            loadHistory()
        }
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

    private suspend fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            getHistoryUseCase.execute().collect { result ->
                result.onSuccess {
                    val lastEpisode = it.last()
                    val lastMovie = getMovie(lastEpisode.movieId)
                    if (lastMovie != null) {
                        _lastViewMovie.postValue(lastMovie!!)
                        loadLastMovieEpisodes(lastMovie, lastEpisode)
                    }
                }.onFailure {
                    dataLoaded()
                    // TODO(Отобразить ошибку загрузки фильмов)
                    Log.e("MOVIES LOADING ERROR", it.message.toString())
                }
            }
        }
    }

    private suspend fun loadLastMovieEpisodes(lastMovie: Movie, lastEpisode: EpisodeView) {
        getEpisodesUseCase.execute(lastMovie.movieId).collect { episodesResult ->
            episodesResult.onSuccess { episodes ->
                dataLoaded()
                _lastViewMovieEpisodesCount.postValue(episodes.size)
                if (episodes.isNotEmpty()) {
                    _lastViewMovieYears.postValue(
                        getMovieYears(EpisodeMapper.mapEpisodes(episodes))
                    )
                    val lastViewEpisode =
                        episodes.singleOrNull { e -> e.episodeId == lastEpisode.episodeId }
                    if (lastViewEpisode != null)
                        _lastViewEpisode.postValue(
                            EpisodeMapper.mapEpisode(
                                lastViewEpisode
                            )
                        )
                }
            }.onFailure {
                dataLoaded()
            }
        }
    }

    private suspend fun loadMovies(
        filter: MovieFilter,
        movies: MutableLiveData<MutableList<Movie>>,
        posters: MutableLiveData<MutableList<MoviePoster>>
    ) {
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

    fun getMovieYears(episodes: List<MovieEpisode>): String {
        var min = episodes[0].year
        var max = episodes[0].year
        for (e in episodes) {
            if (e.year > max)
                max = e.year
            if (e.year < min)
                min = e.year
        }
        if (min == max)
            return min.toString()
        return "$min - $max"
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