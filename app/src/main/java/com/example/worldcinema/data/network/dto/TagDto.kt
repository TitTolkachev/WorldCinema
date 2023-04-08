package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class TagDto(
    val tagId: String,
    val tagName: String,
    val categoryName: String
)
