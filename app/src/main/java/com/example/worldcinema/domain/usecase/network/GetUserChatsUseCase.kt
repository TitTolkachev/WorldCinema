package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IChatRepository
import com.example.worldcinema.domain.model.Chat
import kotlinx.coroutines.flow.Flow

class GetUserChatsUseCase(private val repository: IChatRepository) {

    suspend fun execute(): Flow<Result<List<Chat>>> {
        return repository.getChats()
    }
}