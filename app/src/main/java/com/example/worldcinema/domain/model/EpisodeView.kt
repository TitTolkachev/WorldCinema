package com.example.worldcinema.domain.model

data class EpisodeView(
    val episodeId: String,
    val movieId: String,
    val episodeName: String,
    val movieName: String,
    val preview: String,
    val filepath: String,
    val time: Int
)
