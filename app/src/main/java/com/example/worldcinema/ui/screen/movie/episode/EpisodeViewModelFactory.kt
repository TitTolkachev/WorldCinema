package com.example.worldcinema.ui.screen.movie.episode

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.data.network.requests.auth.AuthRefreshRepository
import com.example.worldcinema.data.network.requests.episodes.EpisodesRepository
import com.example.worldcinema.data.storage.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.token.TokenStorageRepository
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.GetEpisodeTimeUseCase
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.network.SaveEpisodeTimeUseCase
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

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(
            movie,
            episode,
            episodesCount,
            movieYears,
            getEpisodeTimeUseCase,
            saveEpisodeTimeUseCase
        ) as T
    }
}