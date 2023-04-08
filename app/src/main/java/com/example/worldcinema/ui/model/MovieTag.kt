package com.example.worldcinema.ui.model

@kotlinx.serialization.Serializable
data class MovieTag(
    val tagId: String,
    val tagName: String,
    val categoryName: String
) : java.io.Serializable
