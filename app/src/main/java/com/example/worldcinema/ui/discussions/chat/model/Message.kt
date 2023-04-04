package com.example.worldcinema.ui.discussions.chat.model

data class Message(
    val messageId: String,
    val date: String,
    val time: String,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
