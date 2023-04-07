package com.example.worldcinema.data.network.auth

import com.example.worldcinema.data.network.auth.dto.AuthCredentialDto
import com.example.worldcinema.data.network.auth.dto.AuthTokenPairDto
import com.example.worldcinema.data.network.auth.dto.RegistrationBodyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/register")
    suspend fun register(@Body body: RegistrationBodyDto): AuthTokenPairDto

    @POST("api/auth/login")
    suspend fun login(@Body body: AuthCredentialDto): AuthTokenPairDto
}