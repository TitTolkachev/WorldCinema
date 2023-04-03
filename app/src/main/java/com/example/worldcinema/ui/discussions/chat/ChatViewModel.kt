package com.example.worldcinema.ui.discussions.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.discussions.chat.model.Message

class ChatViewModel : ViewModel() {


    private val _messages: MutableLiveData<MutableList<Message>> =
        MutableLiveData(mutableListOf())
    val messages: LiveData<MutableList<Message>> = _messages

    init {
        loadData()
    }

    private fun loadData() {
        // TODO("Not yet implemented")
    }
}