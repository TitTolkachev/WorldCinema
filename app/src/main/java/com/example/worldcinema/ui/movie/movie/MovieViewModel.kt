package com.example.worldcinema.ui.movie.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.worldcinema.R
import com.example.worldcinema.ui.movie.movie.model.ChatInfo
import com.example.worldcinema.ui.movie.movie.model.Movie
import com.example.worldcinema.ui.movie.movie.model.MovieEpisode
import com.example.worldcinema.ui.movie.movie.model.MovieTag

class MovieViewModel(application: Application) : AndroidViewModel(application) {

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
        loadData()
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

    private fun loadData() {
        _movie.value = Movie(
            "1",
            "1",
            "1",
            "6+",
            listOf(ChatInfo("12", "12")),
            listOf("123", "123", "123", "123", "123", "123"),
            "123",
            listOf(
                MovieTag("123", "Мультфильм", "123"),
                MovieTag("123", "Комедия", "123"),
                MovieTag("123", "Ужасы", "123"),
                MovieTag("123", "Боевик", "123"),
                MovieTag("123", "Фантастика", "123"),
                MovieTag("123", "Короткометраж", "123"),
                MovieTag("123", "Трейлер", "123"),
                MovieTag("123", "Советский", "123"),
                MovieTag("123", "Копатыч", "123")
            )
        )
        _movieImages.value = _movie.value?.imageUrls
        _movieAge.value = _movie.value?.age

        loadEpisodes()
    }

    private fun loadEpisodes() {
        _movieEpisodes.value = mutableListOf(
            MovieEpisode(
                "1",
                "Пираты карибского моря и кролик Крош",
                "бла бла бла бла бла бла бла бла",
                listOf("1", "2"),
                "2099",
                listOf("1", "2"),
                1000,
                "",
                ""
            ),
            MovieEpisode(
                "2",
                "Пираты карибского моря и Лунтик на странных берегах",
                "бла бла бла бла бла бла бла бла бла бла бла бла бла бла бла блабла бла бла блабла бла бла бла",
                listOf("1", "2"),
                "1234",
                listOf("1", "2"),
                1000,
                "",
                ""
            )
        )
    }
}