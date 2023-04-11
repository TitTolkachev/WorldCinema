package com.example.worldcinema.domain.i_repository.web_sockets

import androidx.lifecycle.LiveData
import com.example.worldcinema.domain.model.ChatMessage

interface IChatWebSocketRepository {

    fun start(): LiveData<List<ChatMessage>>

    fun stop()

    fun send(message: String)
}