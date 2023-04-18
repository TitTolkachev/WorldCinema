package com.example.worldcinema.ui.screen.discussions.discussions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.GetUserChatsUseCase
import com.example.worldcinema.ui.dialog.AlertType
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

    // Loading
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataLoadedCounter = 0
    private val requestsCount = 1

    // Alert
    private val _showAlertDialog = MutableLiveData(false)
    val showAlertDialog: LiveData<Boolean> = _showAlertDialog

    private val _alertType = MutableLiveData(AlertType.DEFAULT)
    val alertType: LiveData<AlertType> = _alertType

    init {
        loadData()
    }

    private fun loadData() {
        dataLoadedCounter = 0
        viewModelScope.launch(Dispatchers.IO) {
            getUserChatsUseCase.execute().collect { result ->
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
                    dataLoaded()
                }.onFailure {
                    showAlert(AlertType.DEFAULT)
                }
            }
        }
    }

    private fun dataLoaded() {
        if (++dataLoadedCounter == requestsCount)
            _isLoading.postValue(false)
    }

    fun onItemClicked(chatId: String) {
        _selectedChatId.value = chatId
        _showChat.value = true
    }

    fun chatShowed() {
        _showChat.value = false
    }

    fun reload() {
        _isLoading.value = true
        loadData()
    }

    private fun showAlert(alert: AlertType) {
        if (_showAlertDialog.value != true) {
            _alertType.postValue(alert)
            _showAlertDialog.postValue(true)
        }
    }

    fun alertShowed() {
        _showAlertDialog.value = false
        _alertType.value = AlertType.DEFAULT
    }
}