package com.example.worldcinema.ui.discussions.chat.model

import java.util.Date

data class Message(
    val messageId: String,
    val creationDateTime: Date,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
