package com.example.worldcinema.ui.screen.main.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.profile.ProfileRepository
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetUserProfileUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.network.SaveUserAvatarUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class ProfileViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }

    private val userProfileRepository by lazy {
        ProfileRepository(
            AuthNetworkUseCases(
                getTokenFromLocalStorageUseCase,
                saveTokenToLocalStorageUseCase,
                RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
            )
        )
    }

    private val getUserProfileUseCase by lazy {
        GetUserProfileUseCase(userProfileRepository)
    }

    private val saveUserAvatarUseCase by lazy {
        SaveUserAvatarUseCase(userProfileRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            saveTokenToLocalStorageUseCase,
            getUserProfileUseCase,
            saveUserAvatarUseCase
        ) as T
    }
}