package com.example.worldcinema.ui.screen.main.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.collections.CollectionsRepository
import com.example.worldcinema.data.network.requests.cover.CoverRepository
import com.example.worldcinema.data.network.requests.history.HistoryRepository
import com.example.worldcinema.data.network.requests.movie.MovieRepository
import com.example.worldcinema.data.storage.db.CollectionIconRepository
import com.example.worldcinema.data.storage.db.CollectionsDatabase
import com.example.worldcinema.data.storage.shared_prefs.favourites_collection.FavouritesCollectionStorageRepository
import com.example.worldcinema.data.storage.shared_prefs.favourites_collection.SharedPrefFavouritesCollectionStorage
import com.example.worldcinema.data.storage.shared_prefs.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.shared_prefs.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.*
import com.example.worldcinema.domain.usecase.storage.*

class HomeViewModelFactory(
    context: Context, private val favouritesCollectionName: String
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

    private val movieRepository by lazy {
        MovieRepository(
            AuthNetworkUseCases(
                getTokenFromLocalStorageUseCase,
                saveTokenToLocalStorageUseCase,
                RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
            )
        )
    }

    private val getMoviesUseCase by lazy {
        GetMoviesUseCase(movieRepository)
    }

    private val getFavouritesCollectionIdUseCase by lazy {
        GetFavouritesCollectionIdUseCase(
            FavouritesCollectionStorageRepository(
                SharedPrefFavouritesCollectionStorage(context)
            )
        )
    }

    private val saveFavouritesCollectionIdUseCase by lazy {
        SaveFavouritesCollectionIdUseCase(
            FavouritesCollectionStorageRepository(
                SharedPrefFavouritesCollectionStorage(context)
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

    private val createCollectionUseCase by lazy {
        CreateCollectionUseCase(collectionsRepository)
    }

    private val getCollectionsUseCase by lazy {
        GetCollectionsUseCase(collectionsRepository)
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

    private val deleteCollectionsIconsUseCase by lazy {
        DeleteCollectionsIconsUseCase(collectionIconRepository)
    }

    private val getHistoryUseCase by lazy {
        GetHistoryUseCase(
            HistoryRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    private val getEpisodesUseCase by lazy {
        GetEpisodesUseCase(movieRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            favouritesCollectionName,
            getCoverUseCase,
            getMoviesUseCase,
            getFavouritesCollectionIdUseCase,
            saveFavouritesCollectionIdUseCase,
            createCollectionUseCase,
            getCollectionsUseCase,
            deleteCollectionsIconsUseCase,
            getHistoryUseCase,
            getEpisodesUseCase
        ) as T
    }
}