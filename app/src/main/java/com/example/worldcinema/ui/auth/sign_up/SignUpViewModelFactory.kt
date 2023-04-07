package com.example.worldcinema.ui.auth.sign_up

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.auth.AuthRepository
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.network.RegisterUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class SignUpViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }
    private val authRepository by lazy {
        AuthRepository()
    }

    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }
    private val registerUseCase by lazy {
        RegisterUseCase(authRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(
            saveTokenToLocalStorageUseCase,
            registerUseCase
        ) as T
    }
}