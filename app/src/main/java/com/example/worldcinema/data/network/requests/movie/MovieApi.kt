package com.example.worldcinema.data.network.requests.movie

import com.example.worldcinema.data.network.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("api/movies")
    suspend fun getMovies(@Query("filter") filter: String): List<MovieDto>
}