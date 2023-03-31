package com.example.worldcinema.ui.main.compilation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.main.compilation.model.Card

class CompilationViewModel : ViewModel() {

    var cards = MutableLiveData<MutableList<Card>>()

    var isCardStackEmpty = MutableLiveData(false)

    init {
        loadData()
    }

    private fun loadData() {
        val cards = mutableListOf<Card>()
        cards.add(Card("123", "123"))
        cards.add(Card("123", "123"))
        cards.add(Card("123", "123"))

        this.cards.value = cards
    }
}