package com.example.worldcinema.ui.screen.discussions.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.data.network.web_sockets.ChatWebSocketRepository
import com.example.worldcinema.data.network.web_sockets.WebServicesProvider
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.GetChatDataUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.SendChatMessageUseCase
import com.example.worldcinema.domain.usecase.network.web_sockets.StopChatConnectionUseCase


class ChatViewModelFactory(context: Context, chatId: String) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val chatWebSocketRepository = ChatWebSocketRepository(
        WebServicesProvider(
            chatId, AuthNetworkUseCases(
                getTokenFromLocalStorageUseCase,
                saveTokenToLocalStorageUseCase,
                RefreshTokenUseCase(
                    AuthRefreshRepository(
                        getTokenFromLocalStorageUseCase
                    )
                )
            )
        )
    )

    private val getChatDataUseCase by lazy {
        GetChatDataUseCase(chatWebSocketRepository)
    }

    private val sendChatMessageUseCase by lazy {
        SendChatMessageUseCase(chatWebSocketRepository)
    }

    private val stopChatConnectionUseCase by lazy {
        StopChatConnectionUseCase(chatWebSocketRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(
            getChatDataUseCase,
            sendChatMessageUseCase,
            stopChatConnectionUseCase
        ) as T
    }
}