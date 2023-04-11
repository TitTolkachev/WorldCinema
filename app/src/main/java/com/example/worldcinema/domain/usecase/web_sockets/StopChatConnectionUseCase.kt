package com.example.worldcinema.domain.usecase.web_sockets

import com.example.worldcinema.domain.i_repository.web_sockets.IChatWebSocketRepository

class StopChatConnectionUseCase(private val repository: IChatWebSocketRepository) {

    fun execute() {
        return repository.stop()
    }
}