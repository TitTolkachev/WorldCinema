package com.example.worldcinema.ui.screen.main.profile.dialog

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.SaveUserAvatarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileAvatarDialogViewModel(
    private val saveUserAvatarUseCase: SaveUserAvatarUseCase
) : ViewModel() {

    private val _closeDialog = MutableLiveData(false)
    val closeDialog: LiveData<Boolean> = _closeDialog

    private val _remoteImageChanged = MutableLiveData(false)
    val remoteImageChanged: LiveData<Boolean> = _remoteImageChanged

    fun sendAvatarImage(image: Bitmap?) {
        if (image != null)
            viewModelScope.launch(Dispatchers.IO) {
                saveUserAvatarUseCase.execute(image).collect { result ->
                    result.onSuccess {
                        _remoteImageChanged.postValue(true)
                    }.onFailure {
                        // TODO(Показать ошибку, не удалось отправить аватарку)
                        Log.e("AVATAR CHANGE IMAGE RESPONSE", it.message.toString())
                    }
                }
            }
    }

    fun profileReloaded() {
        _closeDialog.postValue(true)
    }
}