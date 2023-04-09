package com.example.worldcinema.ui.model

@kotlinx.serialization.Serializable
data class MovieEpisode(
    val episodeId: String,
    val name: String,
    val description: String,
    val stars: List<String>,
    val year: Int,
    val images: List<String>,
    val runtime: Int,
    val preview: String,
    val filePath: String
) : java.io.Serializable