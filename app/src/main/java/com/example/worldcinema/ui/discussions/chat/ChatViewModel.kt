package com.example.worldcinema.ui.discussions.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.discussions.chat.model.Message

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
                "",
                "3 сентября",
                "",
                "",
                "",
                "",
                ""
            ),
            Message(
                "1",
                "3 сентября",
                "18:00",
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "3 сентября",
                "18:00",
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "3 сентября",
                "18:00",
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "3 сентября",
                "18:00",
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "",
                "Сегодня",
                "",
                "",
                "",
                "",
                ""
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "123",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            ),
            Message(
                "1",
                "4 сентября",
                "16:00",
                "12",
                "Олег Олег",
                "",
                "fsdfsdfsdfsdfsdfsdfdsfsdfsdfsdfsfsdfs"
            )
        )
    }
}