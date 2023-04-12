package com.example.worldcinema.data.network.web_sockets

@kotlinx.serialization.Serializable
data class ChatMessageDto(
    val messageId: String,
    val creationDateTime: String,
    val authorId: String?,
    val authorName: String,
    val authorAvatar: String?,
    val text: String
)
