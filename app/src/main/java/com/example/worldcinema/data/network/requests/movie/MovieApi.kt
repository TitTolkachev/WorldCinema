package com.example.worldcinema.data.network.requests.movie

import com.example.worldcinema.data.network.dto.EpisodeDto
import com.example.worldcinema.data.network.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("api/movies")
    suspend fun getMovies(@Query("filter") filter: String): List<MovieDto>

    @GET("api/movies/{movieId}/episodes")
    suspend fun getEpisodes(@Path("movieId") movieId: String): List<EpisodeDto>

    @POST("api/movies/{movieId}/dislike")
    suspend fun dislikeMovie(@Path("movieId") movieId: String)
}