package com.example.worldcinema.ui.screen.discussions.discussions

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.chats.ChatRepository
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetUserChatsUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class DiscussionsViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val chatRepository by lazy {
        ChatRepository(
            AuthNetworkUseCases(
                getTokenFromLocalStorageUseCase,
                saveTokenToLocalStorageUseCase,
                RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
            )
        )
    }

    private val getUserChatsUseCase by lazy {
        GetUserChatsUseCase(chatRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscussionsViewModel(
            getUserChatsUseCase
        ) as T
    }
}