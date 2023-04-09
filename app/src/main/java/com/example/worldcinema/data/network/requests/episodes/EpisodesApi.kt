package com.example.worldcinema.data.network.requests.episodes

import com.example.worldcinema.data.network.dto.EpisodeTimeDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EpisodesApi {

    @GET("api/episodes/{episodeId}/time")
    suspend fun getTime(@Path("episodeId") episodeId: String): EpisodeTimeDto

    @POST("api/episodes/{episodeId}/time")
    suspend fun saveTime(@Path("episodeId") episodeId: String, @Body body: EpisodeTimeDto)
}