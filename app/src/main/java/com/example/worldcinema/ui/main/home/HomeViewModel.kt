package com.example.worldcinema.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetCoverUseCase
import com.example.worldcinema.ui.main.home.model.MoviePoster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCoverUseCase: GetCoverUseCase
) : ViewModel() {

    private val _coverImage: MutableLiveData<String> =
        MutableLiveData()
    val coverImage: LiveData<String> = _coverImage

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
        loadCover()
        loadData()
    }

    private fun loadCover() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoverUseCase.execute().collect{ result->
                result.onSuccess {
                    _coverImage.postValue(it.backgroundImage)
                }.onFailure {
                    // TODO(Обработать ошибку)
                    Log.e("GET COVER ERROR", it.message.toString())
                }
            }
        }
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