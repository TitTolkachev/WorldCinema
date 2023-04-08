package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class EpisodeDto(
    val episodeId: String,
    val name: String,
    val description: String,
    val director: String,
    val stars: List<String>,
    val year: Int,
    val images: List<String>,
    val runtime: Int,
    val preview: String,
    val filePath: String
)
