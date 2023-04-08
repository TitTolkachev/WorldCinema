package com.example.worldcinema.ui.model

@kotlinx.serialization.Serializable
data class Movie(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val chatInfo: ChatInfo,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<MovieTag>,
) : java.io.Serializable
