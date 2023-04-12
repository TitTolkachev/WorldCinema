package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatInfoUseCase(private val repository: IChatRepository) {

    suspend fun execute(chatId: String): Flow<Result<String>> {
        return repository.getChatInfo(chatId)
    }
}