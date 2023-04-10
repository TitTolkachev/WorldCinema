package com.example.worldcinema.ui.screen.auth.sign_in

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldcinema.domain.model.AuthCredentials
import com.example.worldcinema.domain.usecase.network.LoginUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _navigateToMainScreen: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.execute(AuthCredentials(email, password)).collect { result ->
                result.onSuccess {
                    saveTokenToLocalStorageUseCase.execute(it)
                    _navigateToMainScreen.postValue(true)
                }.onFailure {
                    // TODO(Сделать ошибку входа)
                    Log.e("LOGIN ERROR", it.message.toString())
                }
            }
        }
    }
}