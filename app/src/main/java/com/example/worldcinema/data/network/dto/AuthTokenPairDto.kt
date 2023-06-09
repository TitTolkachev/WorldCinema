package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class AuthTokenPairDto(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val refreshToken: String,
    val refreshTokenExpiresIn: Int
)
