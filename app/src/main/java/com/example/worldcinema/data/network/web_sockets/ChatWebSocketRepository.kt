package com.example.worldcinema.data.network.web_sockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.worldcinema.domain.i_repository.network.web_sockets.IChatWebSocketRepository
import com.example.worldcinema.domain.model.ChatMessage

class ChatWebSocketRepository(private val provider: WebServicesProvider) :
    IChatWebSocketRepository {

    override fun start(): LiveData<List<ChatMessage>> {
        return provider.startSocket().map { list ->
            list.map {
                ChatMessage(
                    it.messageId,
                    it.creationDateTime,
                    it.authorId,
                    it.authorName,
                    it.authorAvatar,
                    it.text
                )
            }
        }
    }

    override fun stop() {
        provider.stopSocket()
    }

    override fun send(message: String) {
        provider.sendMessage(message)
    }
}