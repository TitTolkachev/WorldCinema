package com.example.worldcinema.domain.usecase.web_sockets

import com.example.worldcinema.domain.i_repository.web_sockets.IChatWebSocketRepository

class SendChatMessageUseCase(private val repository: IChatWebSocketRepository) {

    fun execute(message: String) {
        return repository.send(message)
    }
}