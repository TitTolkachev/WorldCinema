package com.example.worldcinema.data.network.dto

@kotlinx.serialization.Serializable
data class CollectionListItemDto(
    val collectionId: String,
    val name:String
)
