package com.example.worldcinema.data.network.web_sockets

import androidx.lifecycle.MutableLiveData
import com.example.worldcinema.data.network.AuthInterceptor
import com.example.worldcinema.data.network.TokenAuthenticator
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

class WebServicesProvider(
    private val chatId: String,
    useCases: AuthNetworkUseCases
) {

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000

        private const val BASE_URL = "ws://107684.web.hosting-russia.ru:8000/api/chats"
    }

    private var _webSocket: WebSocket? = null

    private val socketOkHttpClient = OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .hostnameVerifier { _, _ -> true }
        .addInterceptor(AuthInterceptor(useCases.getTokenFromLocalStorageUseCase))
        .authenticator(
            TokenAuthenticator(
                useCases.getTokenFromLocalStorageUseCase,
                useCases.saveTokenToLocalStorageUseCase,
                useCases.refreshTokenUseCase
            )
        )
        .build()

    private var _webSocketListener: ChatWebSocketListener? = null

    fun startSocket(): MutableLiveData<List<ChatMessageDto>> =
        with(ChatWebSocketListener()) {
            startSocket(this)
            this@with.chat
        }

    private fun startSocket(webSocketListener: ChatWebSocketListener) {
        _webSocketListener = webSocketListener
        _webSocket = socketOkHttpClient.newWebSocket(
            Request.Builder().url(buildString {
                append(BASE_URL)
                append("/$chatId/messages")
            }).build(),
            webSocketListener
        )
        socketOkHttpClient.dispatcher.executorService.shutdown()
    }

    fun stopSocket() {
        try {
            _webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            _webSocket = null
            _webSocketListener?.chat?.value = listOf()
            _webSocketListener = null
        } catch (_: Exception) {
        }
    }

    fun sendMessage(message: String) {
        _webSocket?.send(message)
    }
}