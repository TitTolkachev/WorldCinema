package com.example.worldcinema.ui.screen.main.collections

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.collections.CollectionsRepository
import com.example.worldcinema.data.storage.db.CollectionIconRepository
import com.example.worldcinema.data.storage.db.CollectionsDatabase
import com.example.worldcinema.data.storage.shared_prefs.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.shared_prefs.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetCollectionsUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetCollectionsIconsUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class CollectionsViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val getCollectionsUseCase by lazy {
        GetCollectionsUseCase(
            CollectionsRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    private val collectionIconRepository by lazy {
        CollectionIconRepository(
            Room.databaseBuilder(
                context,
                CollectionsDatabase::class.java,
                CollectionsDatabase.DB_NAME
            ).build()
        )
    }

    private val getCollectionsIconsUseCase by lazy {
        GetCollectionsIconsUseCase(collectionIconRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CollectionsViewModel(
            getCollectionsUseCase,
            getCollectionsIconsUseCase
        ) as T
    }
}