package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class EpisodeViewDto(
    val episodeId: String,
    val movieId: String,
    val episodeName: String,
    val movieName: String,
    val preview: String,
    val filePath: String,
    val time: Int
)
