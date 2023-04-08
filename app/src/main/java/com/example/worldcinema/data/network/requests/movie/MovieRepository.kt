package com.example.worldcinema.data.network.requests.movie

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.data.network.dto.ChatDto
import com.example.worldcinema.data.network.dto.EpisodeDto
import com.example.worldcinema.data.network.dto.MovieDto
import com.example.worldcinema.data.network.dto.TagDto
import com.example.worldcinema.domain.i_repository.network.IMovieRepository
import com.example.worldcinema.domain.model.Chat
import com.example.worldcinema.domain.model.Episode
import com.example.worldcinema.domain.model.Movie
import com.example.worldcinema.domain.model.Tag
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(useCases: AuthNetworkUseCases) : IMovieRepository {

    private val api = AuthNetwork.getMovieApi(useCases)

    override suspend fun getMovies(filter: String): Flow<Result<List<Movie>>> = flow {
        try {
            val data = api.getMovies(filter)
            val movies = mutableListOf<Movie>()
            for (m in data) {
                movies.add(mapMovie(m))
            }
            emit(Result.success(movies.toList()))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getEpisodes(movieId: String): Flow<Result<List<Episode>>> = flow {
        try {
            val data = api.getEpisodes(movieId)
            val episodes = mutableListOf<Episode>()
            for (e in data) {
                episodes.add(mapEpisode(e))
            }
            emit(Result.success(episodes.toList()))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun dislikeMovie(movieId: String): Flow<Result<Boolean>> = flow {
        try {
            api.dislikeMovie(movieId)
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun mapMovie(m: MovieDto): Movie {

        val tags = mutableListOf<Tag>()
        for (t in m.tags) {
            tags.add(mapTag(t))
        }

        return Movie(
            m.movieId,
            m.name,
            m.description,
            m.age,
            mapChat(m.chatInfo),
            m.imageUrls,
            m.poster,
            tags.toList()
        )
    }

    private fun mapEpisode(e: EpisodeDto): Episode {
        return Episode(
            e.episodeId,
            e.name,
            e.description,
            e.director,
            e.stars,
            e.year,
            e.images,
            e.runtime,
            e.preview,
            e.filePath
        )
    }

    private fun mapChat(c: ChatDto): Chat {
        return Chat(c.chatId, c.chatName)
    }

    private fun mapTag(t: TagDto): Tag {
        return Tag(t.tagId, t.tagName, t.categoryName)
    }
}