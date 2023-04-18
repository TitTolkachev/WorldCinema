package com.example.worldcinema.ui.screen.main.profile.dialog

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.usecase.network.SaveUserAvatarUseCase
import com.example.worldcinema.ui.dialog.AlertType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileAvatarDialogViewModel(
    private val saveUserAvatarUseCase: SaveUserAvatarUseCase
) : ViewModel() {

    private val _closeDialog = MutableLiveData(false)
    val closeDialog: LiveData<Boolean> = _closeDialog

    private val _remoteImageChanged = MutableLiveData(false)
    val remoteImageChanged: LiveData<Boolean> = _remoteImageChanged

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    // Alert
    private val _showAlertDialog = MutableLiveData(false)
    val showAlertDialog: LiveData<Boolean> = _showAlertDialog

    private val _alertType = MutableLiveData(AlertType.DEFAULT)
    val alertType: LiveData<AlertType> = _alertType

    fun sendAvatarImage(image: Bitmap?) {
        if (image != null)
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                saveUserAvatarUseCase.execute(image).collect { result ->
                    result.onSuccess {
                        _remoteImageChanged.postValue(true)
                    }.onFailure {
                        showAlert(AlertType.SERVER_ERROR)
                    }
                }
                _isLoading.postValue(false)
            }
    }

    fun profileReloaded() {
        _closeDialog.postValue(true)
    }

    fun showAlert(alert: AlertType) {
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