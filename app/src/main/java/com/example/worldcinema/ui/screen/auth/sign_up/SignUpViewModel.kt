package com.example.worldcinema.ui.screen.auth.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.RegistrationBody
import com.example.worldcinema.domain.usecase.network.RegisterUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _navigateToMainScreen: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    private fun register(name: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUseCase.execute(RegistrationBody(email, password, name, lastName))
                .collect { result ->
                    result.onSuccess {
                        saveTokenToLocalStorageUseCase.execute(it)
                        _navigateToMainScreen.postValue(true)
                    }.onFailure {
                        // TODO(Показывать ошибку регистрации)
                        Log.e("REGISTRATION ERROR", it.message.toString())
                    }
                }
        }
    }

    fun navigatedToMainScreen() {
        _navigateToMainScreen.value = false
    }

    fun onRegisterBtnClick(
        name: String,
        lastName: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {
        if(password.trim() == repeatPassword.trim()) {
            register(name, lastName, email, password.trim())
        } else {
            // TODO(Показать, что пароли не совпадают)
        }
    }
}