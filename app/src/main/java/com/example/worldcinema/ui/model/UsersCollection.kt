package com.example.worldcinema.ui.model

@kotlinx.serialization.Serializable
data class UsersCollection(
    val collectionId: String,
    val imageIndex: Int,
    val name: String
) : java.io.Serializable