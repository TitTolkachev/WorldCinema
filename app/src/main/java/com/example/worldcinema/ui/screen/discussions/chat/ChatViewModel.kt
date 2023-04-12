package com.example.worldcinema.ui.screen.discussions.chat

import androidx.lifecycle.*
import com.example.worldcinema.domain.usecase.network.web_sockets.GetChatDataUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.SendChatMessageUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.StopChatConnectionUseCase
import com.example.worldcinema.ui.helper.ChatDataToMessagesMapper
import com.example.worldcinema.ui.model.Message

class ChatViewModel(
    getChatDataUseCase: GetChatDataUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val stopChatConnectionUseCase: StopChatConnectionUseCase
) : ViewModel() {

    val chatData = getChatDataUseCase.execute()

    private val _messages = MutableLiveData<List<Message>>(listOf())
    val messages: LiveData<List<Message>> = _messages

    private val _userId: MutableLiveData<String> =
        MutableLiveData("")
    val userId: LiveData<String> = _userId

    fun newData() {
        _messages.value = ChatDataToMessagesMapper.mapMessages(chatData.value!!)
    }

    fun onViewDestroyed() {
        stopChatConnectionUseCase.execute()
    }

    fun sendMessage(message: String) {
        sendChatMessageUseCase.execute(message)
    }
}