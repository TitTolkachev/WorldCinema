package com.example.worldcinema.ui.discussions.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.discussions.chat.model.Message
import java.util.*

class ChatViewModel : ViewModel() {


    private val _messages: MutableLiveData<MutableList<Message>> =
        MutableLiveData(mutableListOf())
    val messages: LiveData<MutableList<Message>> = _messages

    private val _userId: MutableLiveData<String> =
        MutableLiveData("")
    val userId: LiveData<String> = _userId

    init {
        loadData()
    }

    private fun loadData() {
        // TODO("Not yet implemented")

        _userId.value = "123"

        _messages.value = mutableListOf(
            Message(
                "1",
                Date(2021, 11, 11),
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                Date(2021, 11, 11),
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                Date(2021, 11, 11),
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                Date(2021, 11, 11),
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                Date(2021, 11, 11),
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                Date(2021, 11, 11),
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            )
        )
    }
}