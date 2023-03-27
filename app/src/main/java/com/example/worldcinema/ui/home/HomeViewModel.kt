package com.example.worldcinema.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.home.model.MoviePoster

class HomeViewModel : ViewModel() {

    private val _trendMovies: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())
    private val _newMovies: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())
    private val _recommendedMovies: MutableLiveData<MutableList<MoviePoster>> =
        MutableLiveData(mutableListOf())
    val trendMovies: LiveData<MutableList<MoviePoster>> = _trendMovies
    val newMovies: LiveData<MutableList<MoviePoster>> = _newMovies
    val recommendedMovies: LiveData<MutableList<MoviePoster>> = _recommendedMovies

    init {
        loadData()
    }

    private fun loadData() {
        _trendMovies.value?.add(MoviePoster("123", "123"))
        _trendMovies.value?.add(MoviePoster("123", "123"))
        _trendMovies.value?.add(MoviePoster("123", "123"))
        _trendMovies.value?.add(MoviePoster("123", "123"))
        _trendMovies.value?.add(MoviePoster("123", "123"))
        _trendMovies.value?.add(MoviePoster("123", "123"))
        _trendMovies.value?.add(MoviePoster("123", "123"))

        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))
        _newMovies.value?.add(MoviePoster("123", "123"))

        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
        _recommendedMovies.value?.add(MoviePoster("123", "123"))
    }

}