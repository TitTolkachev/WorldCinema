package com.example.worldcinema.ui.screen.auth.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.AuthValidationError
import com.example.worldcinema.domain.model.RegistrationBody
import com.example.worldcinema.domain.usecase.network.RegisterUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.domain.usecase.validation.ValidateAuthUseCase
import com.example.worldcinema.ui.dialog.AlertType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val registerUseCase: RegisterUseCase,
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

    private fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {

        when (validateAuthUseCase.execute(name, lastName, email, password, repeatPassword)) {
            AuthValidationError.EMPTY_FIRST_NAME -> {
                _alertType.value = AlertType.EMPTY_FIRST_NAME
                _showAlertDialog.value = true
            }
            AuthValidationError.EMPTY_LAST_NAME -> {
                _alertType.value = AlertType.EMPTY_LAST_NAME
                _showAlertDialog.value = true
            }
            AuthValidationError.EMPTY_EMAIL -> {
                _alertType.value = AlertType.EMPTY_EMAIL
                _showAlertDialog.value = true
            }
            AuthValidationError.EMPTY_PASSWORD -> {
                _alertType.value = AlertType.EMPTY_PASSWORD
                _showAlertDialog.value = true
            }
            AuthValidationError.EMPTY_REPEAT_PASSWORD -> {
                _alertType.value = AlertType.EMPTY_REPEAT_PASSWORD
                _showAlertDialog.value = true
            }
            AuthValidationError.WRONG_EMAIL -> {
                _alertType.value = AlertType.WRONG_EMAIL
                _showAlertDialog.value = true
            }
            AuthValidationError.WRONG_REPEAT_PASSWORD -> {
                _alertType.value = AlertType.WRONG_REPEAT_PASSWORD
                _showAlertDialog.value = true
            }
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    registerUseCase.execute(RegistrationBody(email, password, name, lastName))
                        .collect { result ->
                            result.onSuccess {
                                saveTokenToLocalStorageUseCase.execute(it)
                                _navigateToMainScreen.postValue(true)
                            }.onFailure {
                                _alertType.postValue(AlertType.REGISTER_NOT_SUCCESS)
                                _showAlertDialog.postValue(true)
                            }
                        }
                }
            }
        }
    }

    fun onRegisterBtnClick(
        name: String,
        lastName: String,
        email: String,
        password: String,
        repeatPassword: String,
    ) {
        register(name, lastName, email, password.trim(), repeatPassword.trim())
    }
}