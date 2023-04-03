package com.example.worldcinema.ui.movie.movie.model

data class Movie(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val chatInfo: List<ChatInfo>,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<MovieTag>,

)
