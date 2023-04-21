package com.example.worldcinema.ui.screen.auth.sign_in

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRepository
import com.example.worldcinema.data.storage.shared_prefs.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.shared_prefs.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.network.LoginUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.domain.usecase.validation.ValidateAuthUseCase

class SignInViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }
    private val authRepository by lazy {
        AuthRepository()
    }

    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }
    private val loginUseCase by lazy {
        LoginUseCase(authRepository)
    }
    private val validateAuthUseCase by lazy {
        ValidateAuthUseCase()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(
            saveTokenToLocalStorageUseCase,
            loginUseCase,
            validateAuthUseCase
        ) as T
    }
}