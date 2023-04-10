package com.example.worldcinema.ui.screen.discussions.discussions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.model.Discussion

class DiscussionsViewModel : ViewModel() {

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
        _discussions.value?.add(
            Discussion(
            "10", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "9", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "8", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "7", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "6", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "5", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "4", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "3", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "2", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )

        _discussions.value?.add(
            Discussion(
                "1", "", "",
                "Пираты Карибского моря и пчелиный улей", "ОЛеГ",
                "блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла блаблабла бла бла блаблабла бла бла блаблабла бла блаблаблабла бла бла")
        )
    }

    fun onItemClicked(chatId: String) {
        _selectedChatId.value = chatId
        _showChat.value = true
    }

    fun chatShowed() {
        _showChat.value = false
    }
}