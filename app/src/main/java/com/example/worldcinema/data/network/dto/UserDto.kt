package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class UserDto(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?
)
