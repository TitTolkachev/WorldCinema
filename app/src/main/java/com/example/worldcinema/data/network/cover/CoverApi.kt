package com.example.worldcinema.data.network.cover

import com.example.worldcinema.data.network.cover.dto.CoverDto
import retrofit2.http.GET

interface CoverApi {

    @GET("api/cover")
    suspend fun getCover(): CoverDto
}