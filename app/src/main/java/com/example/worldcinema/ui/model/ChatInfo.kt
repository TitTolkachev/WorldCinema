package com.example.worldcinema.ui.model

@kotlinx.serialization.Serializable
data class ChatInfo(
    val chatId: String,
    val chatName: String
) : java.io.Serializable
