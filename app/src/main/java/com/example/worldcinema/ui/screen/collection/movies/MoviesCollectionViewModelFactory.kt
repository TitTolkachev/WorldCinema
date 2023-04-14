package com.example.worldcinema.ui.screen.collection.movies

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.collections.CollectionsRepository
import com.example.worldcinema.data.storage.favourites_collection.FavouritesCollectionStorageRepository
import com.example.worldcinema.data.storage.favourites_collection.SharedPrefFavouritesCollectionStorage
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetMoviesInCollectionUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.ui.model.UsersCollection

class MoviesCollectionViewModelFactory(
    context: Context,
    private val collection: UsersCollection
) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val getMoviesInCollectionUseCase by lazy {
        GetMoviesInCollectionUseCase(
            CollectionsRepository(
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
            FavouritesCollectionStorageRepository(SharedPrefFavouritesCollectionStorage(context))
        )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesCollectionViewModel(
            collection,
            getMoviesInCollectionUseCase,
            getFavouritesCollectionIdUseCase
        ) as T
    }
}