package com.example.worldcinema.domain.model

data class ChatMessage(
    val messageId: String,
    val creationDateTime: String,
    val authorId: String?,
    val authorName: String,
    val authorAvatar: String?,
    val text: String
)