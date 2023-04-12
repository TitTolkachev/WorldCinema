package com.example.worldcinema.data.network.requests.chats

import com.example.worldcinema.data.network.dto.ChatDto
import com.example.worldcinema.data.network.dto.ChatInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatsApi {

    @GET("api/chats")
    suspend fun getChats(): List<ChatDto>

    @GET("api/chats/{chatId}")
    suspend fun getChatInfo(@Path("chatId") chatId: String): ChatInfoDto
}