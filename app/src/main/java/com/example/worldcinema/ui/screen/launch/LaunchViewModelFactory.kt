package com.example.worldcinema.ui.screen.launch

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.cover.CoverRepository
import com.example.worldcinema.data.storage.favourites_collection.FavouritesCollectionStorageRepository
import com.example.worldcinema.data.storage.favourites_collection.SharedPrefFavouritesCollectionStorage
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetCoverUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class LaunchViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
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

    private val getFavouritesCollectionIdUseCase by lazy {
        GetFavouritesCollectionIdUseCase(
            FavouritesCollectionStorageRepository(
                SharedPrefFavouritesCollectionStorage(context)
            )
        )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LaunchViewModel(
            getCoverUseCase,
            getFavouritesCollectionIdUseCase
        ) as T
    }
}