package com.example.worldcinema.data.network.requests.history

import com.example.worldcinema.data.network.dto.EpisodeViewDto
import retrofit2.http.GET

interface HistoryApi {

    @GET("api/history")
    suspend fun getHistory(): List<EpisodeViewDto>
}