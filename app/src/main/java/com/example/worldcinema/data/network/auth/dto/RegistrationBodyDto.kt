package com.example.worldcinema.data.network.auth.dto

@kotlinx.serialization.Serializable
data class RegistrationBodyDto(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
