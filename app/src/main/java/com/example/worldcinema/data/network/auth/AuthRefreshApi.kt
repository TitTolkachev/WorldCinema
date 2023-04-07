package com.example.worldcinema.data.network.auth

import com.example.worldcinema.data.network.auth.dto.AuthTokenPairDto
import retrofit2.http.POST

interface AuthRefreshApi {

    @POST("api/auth/refresh")
    suspend fun refresh(): AuthTokenPairDto
}