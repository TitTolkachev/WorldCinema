package com.example.worldcinema.data.network.requests.cover

import com.example.worldcinema.data.network.dto.CoverDto
import retrofit2.http.GET

interface CoverApi {

    @GET("api/cover")
    suspend fun getCover(): CoverDto
}