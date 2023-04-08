package com.example.worldcinema.ui.screen.main.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class ProfileViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            saveTokenToLocalStorageUseCase
        ) as T
    }
}