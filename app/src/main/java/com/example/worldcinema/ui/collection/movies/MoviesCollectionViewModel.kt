package com.example.worldcinema.ui.collection.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.collection.movies.model.MovieInCollection

class MoviesCollectionViewModel : ViewModel() {

    private val _movies: MutableLiveData<MutableList<MovieInCollection>> =
        MutableLiveData(mutableListOf())

    val movies: LiveData<MutableList<MovieInCollection>> = _movies

    init {
        loadData()
    }

    fun onItemClicked(movieId: String) {
        // TODO(По идее что-то должно происходить, но в тз пусто)
    }

    private fun loadData() {
        _movies.value = mutableListOf(
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
            MovieInCollection("", "KLSDJFoidsfs:FLKj", "FSDfsDfsdfsdFSDFdsfSDfSdfsDfSdfSdfsdfsdfSdfsFsd"),
        )
    }
}