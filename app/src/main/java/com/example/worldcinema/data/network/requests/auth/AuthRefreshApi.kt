package com.example.worldcinema.data.network.requests.auth

import com.example.worldcinema.data.network.dto.AuthTokenPairDto
import retrofit2.http.POST

interface AuthRefreshApi {

    @POST("api/auth/refresh")
    suspend fun refresh(): AuthTokenPairDto
}