package com.example.worldcinema.ui.screen.movie.episode

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode

class EpisodeViewModelFactory(
    context: Context,
    private val movie: Movie,
    private val episode: MovieEpisode,
    private val episodesCount: Int,
    private val movieYears: String
) : ViewModelProvider.Factory {

//    private val tokenRepository by lazy {
//        TokenStorageRepository(SharedPrefTokenStorage(context))
//    }
//
//    private val getTokenFromLocalStorageUseCase by lazy {
//        GetTokenFromLocalStorageUseCase(tokenRepository)
//    }
//    private val saveTokenToLocalStorageUseCase by lazy {
//        SaveTokenToLocalStorageUseCase(tokenRepository)
//    }
//
//    private val getEpisodesUseCase by lazy {
//        GetEpisodesUseCase(
//            MovieRepository(
//                AuthNetworkUseCases(
//                    getTokenFromLocalStorageUseCase,
//                    saveTokenToLocalStorageUseCase,
//                    RefreshTokenUseCase(AuthRefreshRepository(getTokenFromLocalStorageUseCase))
//                )
//            )
//        )
//    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(
            movie,
            episode,
            episodesCount,
            movieYears
        ) as T
    }
}