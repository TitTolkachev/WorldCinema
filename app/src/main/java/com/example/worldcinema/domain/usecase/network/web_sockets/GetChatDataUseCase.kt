package com.example.worldcinema.domain.usecase.network.web_sockets

import androidx.lifecycle.LiveData
import com.example.worldcinema.domain.i_repository.network.web_sockets.IChatWebSocketRepository
import com.example.worldcinema.domain.model.ChatMessage

class GetChatDataUseCase(private val repository: IChatWebSocketRepository) {

    fun execute(): LiveData<List<ChatMessage>> {
        return repository.start()
    }
}