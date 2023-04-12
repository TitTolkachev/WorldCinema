package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun getChats(): Flow<Result<List<Chat>>>

    suspend fun getChatInfo(chatId: String): Flow<Result<String>>
}