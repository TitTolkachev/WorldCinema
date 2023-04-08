package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class MovieDto(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val chatInfo: ChatDto,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<TagDto>
)
