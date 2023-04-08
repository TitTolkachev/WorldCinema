package com.example.worldcinema.ui.screen.movie.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.R
import com.example.worldcinema.domain.usecase.network.GetEpisodesUseCase
import com.example.worldcinema.ui.helper.EpisodeMapper
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    movie: Movie,
    private val getEpisodesUseCase: GetEpisodesUseCase
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

    init {
        _movie.value = movie
        _movieImages.value = _movie.value?.imageUrls
        _movieAge.value = _movie.value?.age
        loadEpisodes()
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

    private fun loadEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            getEpisodesUseCase.execute(_movie.value?.movieId ?: "").collect { result ->
                result.onSuccess {
                    _movieEpisodes.postValue(EpisodeMapper.mapEpisodes(it))
                }.onFailure {
                    // TODO(Показать ошибку)
                    Log.e("EPISODE LOADING ERROR", it.message.toString())
                }
            }
        }
    }
}