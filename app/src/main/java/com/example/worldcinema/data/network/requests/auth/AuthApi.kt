package com.example.worldcinema.data.network.requests.auth

import com.example.worldcinema.data.network.dto.AuthCredentialDto
import com.example.worldcinema.data.network.dto.AuthTokenPairDto
import com.example.worldcinema.data.network.dto.RegistrationBodyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/register")
    suspend fun register(@Body body: RegistrationBodyDto): AuthTokenPairDto

    @POST("api/auth/login")
    suspend fun login(@Body body: AuthCredentialDto): AuthTokenPairDto
}