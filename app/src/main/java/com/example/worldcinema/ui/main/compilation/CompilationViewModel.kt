package com.example.worldcinema.ui.main.compilation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompilationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is compilation Fragment"
    }
    val text: LiveData<String> = _text
}