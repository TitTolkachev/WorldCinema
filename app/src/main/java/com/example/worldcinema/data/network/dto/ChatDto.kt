package com.example.worldcinema.data.network.dto

import com.example.worldcinema.data.network.web_sockets.ChatMessageDto

@kotlinx.serialization.Serializable
data class ChatDto(
    val chatId: String,
    val chatName: String,
    val lastMessage: ChatMessageDto?
)
