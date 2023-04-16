package com.example.worldcinema.ui.screen.movie.episode

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.collections.CollectionsRepository
import com.example.worldcinema.data.network.requests.episodes.EpisodesRepository
import com.example.worldcinema.data.storage.shared_prefs.favourites_collection.FavouritesCollectionStorageRepository
import com.example.worldcinema.data.storage.shared_prefs.favourites_collection.SharedPrefFavouritesCollectionStorage
import com.example.worldcinema.data.storage.shared_prefs.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.shared_prefs.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.*
import com.example.worldcinema.domain.usecase.storage.GetFavouritesCollectionIdUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode

class EpisodeViewModelFactory(
    context: Context,
    private val movie: Movie,
    private val episode: MovieEpisode,
    private val episodesCount: Int,
    private val movieYears: String
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

    private val getEpisodeTimeUseCase by lazy {
        GetEpisodeTimeUseCase(
            EpisodesRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    private val saveEpisodeTimeUseCase by lazy {
        SaveEpisodeTimeUseCase(
            EpisodesRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
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

    private val getFavouritesCollectionIdUseCase by lazy {
        GetFavouritesCollectionIdUseCase(
            FavouritesCollectionStorageRepository(SharedPrefFavouritesCollectionStorage(context))
        )
    }

    private val addMovieToCollectionUseCase by lazy {
        AddMovieToCollectionUseCase(
            CollectionsRepository(
                AuthNetworkUseCases(
                    getTokenFromLocalStorageUseCase,
                    saveTokenToLocalStorageUseCase,
                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
                )
            )
        )
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(
            movie,
            episode,
            episodesCount,
            movieYears,
            getEpisodeTimeUseCase,
            saveEpisodeTimeUseCase,
            getCollectionsUseCase,
            getFavouritesCollectionIdUseCase,
            addMovieToCollectionUseCase
        ) as T
    }
}