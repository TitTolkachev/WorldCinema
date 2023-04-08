package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class CoverDto(
    val backgroundImage: String,
    val foregroundImage: String
)
