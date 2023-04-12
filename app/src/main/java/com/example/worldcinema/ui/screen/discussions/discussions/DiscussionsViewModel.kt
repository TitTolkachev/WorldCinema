package com.example.worldcinema.ui.screen.discussions.discussions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetUserChatsUseCase
import com.example.worldcinema.ui.model.Discussion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscussionsViewModel(
    private val getUserChatsUseCase: GetUserChatsUseCase
) : ViewModel() {

    private val _discussions: MutableLiveData<MutableList<Discussion>> =
        MutableLiveData(mutableListOf())
    val discussions: LiveData<MutableList<Discussion>> = _discussions

    private val _showChat: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val showChat: LiveData<Boolean> = _showChat

    private val _selectedChatId: MutableLiveData<String> =
        MutableLiveData("")
    val selectedChatId: LiveData<String> = _selectedChatId

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserChatsUseCase.execute().collect{result->
                result.onSuccess { chats ->
                    _discussions.postValue(chats.map {
                        Discussion(
                            it.chatId,
                            it.lastMessage?.authorAvatar ?: "",
                            it.chatName,
                            it.lastMessage?.authorName ?: "",
                            it.lastMessage?.text ?: ""
                        )
                    }.toMutableList())
                }.onFailure {
                    // TODO(Показать ошибку)
                }
            }
        }
    }

    fun onItemClicked(chatId: String) {
        _selectedChatId.value = chatId
        _showChat.value = true
    }

    fun chatShowed() {
        _showChat.value = false
    }
}