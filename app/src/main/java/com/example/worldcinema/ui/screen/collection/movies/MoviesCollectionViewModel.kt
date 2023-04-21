package com.example.worldcinema.ui.screen.collection.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetMoviesInCollectionUseCase
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.helper.MovieMapper
import com.example.worldcinema.ui.model.MovieInCollection
import com.example.worldcinema.ui.model.UsersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesCollectionViewModel(
    private val collection: UsersCollection,
    private val getMoviesInCollectionUseCase: GetMoviesInCollectionUseCase,
    getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase
) : ViewModel() {

    private val _movies: MutableLiveData<MutableList<MovieInCollection>> =
        MutableLiveData(mutableListOf())
    val movies: LiveData<MutableList<MovieInCollection>> = _movies

    private val _isCollectionFavourite = MutableLiveData(false)
    val isCollectionFavourite: LiveData<Boolean> = _isCollectionFavourite

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataLoadedCounter = 0
    private val requestsCount = 1

    // Alert
    private val _showAlertDialog = MutableLiveData(false)
    val showAlertDialog: LiveData<Boolean> = _showAlertDialog

    private val _alertType = MutableLiveData(AlertType.DEFAULT)
    val alertType: LiveData<AlertType> = _alertType

    init {
        _isLoading.value = true
        dataLoadedCounter = 0
        if (collection.collectionId == getFavouritesCollectionIdUseCase.execute())
            _isCollectionFavourite.value = true
        loadMovies()
    }

    fun getCollectionId(): String {
        return collection.collectionId
    }

    private fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesInCollectionUseCase.execute(collection.collectionId).collect { result ->
                result.onSuccess {
                    dataLoaded()
                    _movies.postValue(MovieMapper.mapMoviesToCollectionMovies(it))
                }.onFailure {
                    _alertType.postValue(AlertType.DEFAULT)
                    _showAlertDialog.postValue(true)
                }
            }
        }
    }

    fun reload() {
        _isLoading.value = true
        dataLoadedCounter = 0
        loadMovies()
    }

    private fun dataLoaded() {
        if (++dataLoadedCounter == requestsCount)
            _isLoading.postValue(false)
    }
}