package com.example.worldcinema.ui.movie.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.movie.movie.model.ChatInfo
import com.example.worldcinema.ui.movie.movie.model.Movie
import com.example.worldcinema.ui.movie.movie.model.MovieTag

class MovieViewModel : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _movieImages: MutableLiveData<List<String>> =
        MutableLiveData(mutableListOf())
    val movieImages: MutableLiveData<List<String>> = _movieImages

    init {
        loadData()
    }

    private fun loadData() {
        _movie.value = Movie(
            "1",
            "1",
            "1",
            "6+",
            listOf(ChatInfo("12", "12")),
            listOf("123", "123", "123", "123", "123", "123"),
            "123",
            listOf(MovieTag("123", "Мультфильм", "123"))
        )
        _movieImages.value = _movie.value?.imageUrls
    }
}