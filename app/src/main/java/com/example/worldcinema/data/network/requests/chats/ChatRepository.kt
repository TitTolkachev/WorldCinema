package com.example.worldcinema.data.network.requests.chats

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.domain.i_repository.network.IChatRepository
import com.example.worldcinema.domain.model.Chat
import com.example.worldcinema.domain.model.ChatMessage
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChatRepository(useCases: AuthNetworkUseCases) : IChatRepository {

    private val api = AuthNetwork.getChatsApi(useCases)

    override suspend fun getChats(): Flow<Result<List<Chat>>> = flow {
        try {
            val data = api.getChats()
            emit(Result.success(data.map {
                Chat(
                    it.chatId, it.chatName, if (it.lastMessage != null) ChatMessage(
                        it.lastMessage.messageId,
                        it.lastMessage.creationDateTime,
                        it.lastMessage.authorId,
                        it.lastMessage.authorName,
                        it.lastMessage.authorAvatar,
                        it.lastMessage.text,
                    ) else null
                )
            }))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getChatInfo(chatId: String): Flow<Result<String>> = flow {
        try {
            val data = api.getChatInfo(chatId)
            emit(Result.success(data.chatName))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}