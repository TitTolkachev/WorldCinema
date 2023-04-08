package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class AuthCredentialDto(
    val email: String,
    val password: String
)
