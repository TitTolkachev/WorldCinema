package com.example.worldcinema.data.web_sockets

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.worldcinema.data.web_sockets.WebServicesProvider.Companion.NORMAL_CLOSURE_STATUS
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocketListener : WebSocketListener() {

    val chat = MutableLiveData<List<ChatMessageDto>>(listOf())

    override fun onOpen(webSocket: WebSocket, response: Response) {
//        webSocket.send("Hi")
//        webSocket.send("Hi again")
//        webSocket.send("Hi again again")
//        webSocket.send("Hi again again again")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WEB SOCKET CONNECTION ERROR", text)
        val data = chat.value?.toMutableList()
        data?.add(Json.decodeFromString(text))
        chat.postValue(data?.toList())
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