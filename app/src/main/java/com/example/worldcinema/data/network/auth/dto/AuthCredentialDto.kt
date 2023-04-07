package com.example.worldcinema.data.network.auth.dto

@kotlinx.serialization.Serializable
data class AuthCredentialDto(
    val email: String,
    val password: String
)
