package com.example.worldcinema.ui.screen.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class ProfileViewModel(private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase) :
    ViewModel() {

    private val _shouldExit: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val shouldExit: LiveData<Boolean> = _shouldExit

    fun onExitBtnClick() {
        saveTokenToLocalStorageUseCase.execute(Token("", ""))
        _shouldExit.value = true
    }

    fun onExited() {
        _shouldExit.value = false
    }
}