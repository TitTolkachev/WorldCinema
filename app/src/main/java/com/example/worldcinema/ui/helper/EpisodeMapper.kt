package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.Episode
import com.example.worldcinema.ui.model.MovieEpisode

object EpisodeMapper {

    fun mapEpisode(e: Episode): MovieEpisode {
        return MovieEpisode(
            e.episodeId,
            e.name,
            e.description,
            e.stars,
            e.year,
            e.images,
            e.runtime,
            e.preview,
            e.filePath
        )
    }

    fun mapEpisodes(episodes: List<Episode>): MutableList<MovieEpisode> {
        val res = mutableListOf<MovieEpisode>()
        for (e in episodes)
            res.add(mapEpisode(e))
        return res
    }
}