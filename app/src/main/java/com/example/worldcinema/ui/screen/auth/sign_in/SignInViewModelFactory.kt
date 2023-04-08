package com.example.worldcinema.ui.screen.auth.sign_in

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.auth.AuthRepository
import com.example.worldcinema.data.network.requests.cover.CoverRepository
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetCoverUseCase
import com.example.worldcinema.domain.usecase.network.LoginUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class SignInViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }
    private val authRepository by lazy {
        AuthRepository()
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }
    private val loginUseCase by lazy {
        LoginUseCase(authRepository)
    }
    private val getCoverUseCase by lazy {
        GetCoverUseCase(
            CoverRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(
            saveTokenToLocalStorageUseCase,
            loginUseCase,
            getCoverUseCase
        ) as T
    }
}