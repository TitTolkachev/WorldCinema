package com.example.worldcinema.ui.model

data class MovieEpisode(
    val episodeId: String,
    val name: String,
    val description: String,
    val starts: List<String>,
    val year: String,
    val images: List<String>,
    val runtime: Int,
    val preview: String,
    val filePath: String
)