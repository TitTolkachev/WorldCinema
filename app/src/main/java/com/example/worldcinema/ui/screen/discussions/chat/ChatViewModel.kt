package com.example.worldcinema.ui.screen.discussions.chat

import android.util.Log
import androidx.lifecycle.*
import com.example.worldcinema.domain.usecase.network.GetChatInfoUseCase
import com.example.worldcinema.domain.usecase.network.GetUserProfileUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.GetChatDataUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.SendChatMessageUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.StopChatConnectionUseCase
import com.example.worldcinema.ui.helper.ChatDataToMessagesMapper
import com.example.worldcinema.ui.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    getChatDataUseCase: GetChatDataUseCase,
    private val chatId: String,
    private val getChatInfoUseCase: GetChatInfoUseCase,
    private val sendChatMessageUseCase: SendChatMessageUseCase,
    private val stopChatConnectionUseCase: StopChatConnectionUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    val chatData = getChatDataUseCase.execute()

    private val _messages = MutableLiveData<List<Message>>(listOf())
    val messages: LiveData<List<Message>> = _messages

    private val _userId: MutableLiveData<String> =
        MutableLiveData("")
    val userId: LiveData<String> = _userId

    private val _chatName: MutableLiveData<String> =
        MutableLiveData("")
    val chatName: LiveData<String> = _chatName


    init {
        loadChatName()
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserProfileUseCase.execute().collect { result ->
                result.onSuccess {
                    _userId.postValue(it.userId)
                }.onFailure {
                    // TODO(Показать ошибку)
                    Log.e("PROFILE LOADING ERROR", it.message.toString())
                }
            }
        }
    }

    private fun loadChatName() {
        viewModelScope.launch(Dispatchers.IO) {
            getChatInfoUseCase.execute(chatId).collect { result ->
                result.onSuccess {
                    _chatName.postValue(it)
                }.onFailure {
                    // TODO(Показать ошибку)
                    Log.e("CHAT NAME ERROR", it.message.toString())
                }
            }
        }
    }

    fun newData() {
        _messages.value = ChatDataToMessagesMapper.mapMessages(chatData.value!!)
    }

    fun onViewDestroyed() {
        stopChatConnectionUseCase.execute()
    }

    fun sendMessage(message: String) {
        if (message.trim().isNotEmpty())
            sendChatMessageUseCase.execute(message)
    }
}