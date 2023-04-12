package com.example.worldcinema.data.network.web_sockets

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.worldcinema.data.network.web_sockets.WebServicesProvider.Companion.NORMAL_CLOSURE_STATUS
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocketListener : WebSocketListener() {

    val chat = MutableLiveData<List<ChatMessageDto>>(listOf())

    override fun onOpen(webSocket: WebSocket, response: Response) {

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("WEB SOCKET DATA RECEIVED", text)
            val data = chat.value?.toMutableList()
            data?.add(Json.decodeFromString(text))
            chat.value = data?.toList()
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        chat.postValue(listOf())
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        chat.postValue(listOf())
        Log.e("WEB SOCKET CONNECTION ERROR", t.message.toString())
    }
}