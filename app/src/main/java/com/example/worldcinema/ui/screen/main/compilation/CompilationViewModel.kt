package com.example.worldcinema.ui.screen.main.compilation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.model.MovieFilter
import com.example.worldcinema.domain.usecase.network.GetMoviesUseCase
import com.example.worldcinema.ui.helper.MovieMapper
import com.example.worldcinema.ui.helper.MovieToCardMapper
import com.example.worldcinema.ui.model.Card
import com.example.worldcinema.ui.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompilationViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {

    private val _isCardStackEmpty: MutableLiveData<Boolean> =
        MutableLiveData(true)
    var isCardStackEmpty: LiveData<Boolean> = _isCardStackEmpty

    private val _movies: MutableLiveData<MutableList<Movie>> =
        MutableLiveData(mutableListOf())

    private val _cards: MutableLiveData<MutableList<Card>> =
        MutableLiveData(mutableListOf())
    var cards: LiveData<MutableList<Card>> = _cards

    private var swipedCardsCount = 0

    private val _displayedTitle: MutableLiveData<String> =
        MutableLiveData()
    var displayedTitle: LiveData<String> = _displayedTitle

    init {
        loadData()
    }

    fun like() {
        swipedCardsCount++
        if (swipedCardsCount == _movies.value?.size) {
            _isCardStackEmpty.value = true
        } else {
            _displayedTitle.value = _movies.value?.get(swipedCardsCount)?.name ?: ""
        }
    }

    fun skip() {
        swipedCardsCount++
        if (swipedCardsCount == _cards.value?.size) {
            _isCardStackEmpty.value = true
        } else {
            _displayedTitle.value = _movies.value?.get(swipedCardsCount)?.name ?: ""
        }
    }

    fun onViewResume() {

    }

    private fun loadData() {

        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.execute(MovieFilter.Compilation).collect { result ->
                result.onSuccess {
                    val data = MovieMapper.mapMovies(it)
                    _movies.postValue(data)
                    _cards.postValue(MovieToCardMapper.mapMovies(data))
                    if (data.isNotEmpty()) {
                        _isCardStackEmpty.postValue(false)
                        _displayedTitle.postValue(data[0].name)
                    }
                }.onFailure {
                    // TODO(Отобразить ошибку загрузки фильмов)
                    Log.e("MOVIES LOADING ERROR", it.message.toString())
                }
            }
        }
    }
}