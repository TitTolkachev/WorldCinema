package com.example.worldcinema.ui.screen.main.compilation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.collections.CollectionsRepository
import com.example.worldcinema.data.network.requests.movie.MovieRepository
import com.example.worldcinema.data.storage.favourites_collection.FavouritesCollectionStorageRepository
import com.example.worldcinema.data.storage.favourites_collection.SharedPrefFavouritesCollectionStorage
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.*
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class CompilationViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val tokenRepository by lazy {
        TokenStorageRepository(SharedPrefTokenStorage(context))
    }

    private val getTokenFromLocalStorageUseCase by lazy {
        GetTokenFromLocalStorageUseCase(tokenRepository)
    }
    private val saveTokenToLocalStorageUseCase by lazy {
        SaveTokenToLocalStorageUseCase(tokenRepository)
    }

    private val getMoviesUseCase by lazy {
        GetMoviesUseCase(
            MovieRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    private val dislikeMovieUseCase by lazy {
        DislikeMovieUseCase(
            MovieRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    private val collectionsRepository by lazy {
        CollectionsRepository(
            AuthNetworkUseCases(
                getTokenFromLocalStorageUseCase,
                saveTokenToLocalStorageUseCase,
                RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
            )
        )
    }

    private val getMoviesInCollectionUseCase by lazy {
        GetMoviesInCollectionUseCase(collectionsRepository)
    }

    private val addMovieToCollectionUseCase by lazy {
        AddMovieToCollectionUseCase(collectionsRepository)
    }

    private val deleteMovieFromCollectionUseCase by lazy {
        DeleteMovieFromCollectionUseCase(collectionsRepository)
    }

    private val getFavouritesCollectionIdUseCase by lazy {
        GetFavouritesCollectionIdUseCase(
            FavouritesCollectionStorageRepository(
                SharedPrefFavouritesCollectionStorage(context)
            )
        )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CompilationViewModel(
            getFavouritesCollectionIdUseCase,
            getMoviesUseCase,
            getMoviesInCollectionUseCase,
            dislikeMovieUseCase,
            addMovieToCollectionUseCase,
            deleteMovieFromCollectionUseCase
        ) as T
    }
}