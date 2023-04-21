package com.example.worldcinema.ui.screen.auth.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.AuthCredentials
import com.example.worldcinema.domain.model.AuthValidationError
import com.example.worldcinema.domain.usecase.network.LoginUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.domain.usecase.validation.ValidateAuthUseCase
import com.example.worldcinema.ui.dialog.AlertType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val loginUseCase: LoginUseCase,
    private val validateAuthUseCase: ValidateAuthUseCase
) : ViewModel() {

    private val _navigateToMainScreen: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    // Alert
    private val _showAlertDialog = MutableLiveData(false)
    val showAlertDialog: LiveData<Boolean> = _showAlertDialog

    private val _alertType = MutableLiveData(AlertType.DEFAULT)
    val alertType: LiveData<AlertType> = _alertType

    fun login(email: String, password: String) {

        when (validateAuthUseCase.execute(email = email, password = password)) {
            AuthValidationError.EMPTY_EMAIL -> {
                _alertType.value = AlertType.EMPTY_EMAIL
                _showAlertDialog.value = true
            }
            AuthValidationError.EMPTY_PASSWORD -> {
                _alertType.value = AlertType.EMPTY_PASSWORD
                _showAlertDialog.value = true
            }
            AuthValidationError.WRONG_EMAIL -> {
                _alertType.value = AlertType.WRONG_EMAIL
                _showAlertDialog.value = true
            }
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loginUseCase.execute(AuthCredentials(email, password)).collect { result ->
                        result.onSuccess {
                            saveTokenToLocalStorageUseCase.execute(it)
                            _navigateToMainScreen.postValue(true)
                        }.onFailure {
                            _alertType.postValue(AlertType.AUTH_NOT_SUCCESS)
                            _showAlertDialog.postValue(true)
                        }
                    }
                }
            }
        }
    }
}