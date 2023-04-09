package com.example.worldcinema.data.network

import com.example.worldcinema.data.network.requests.cover.CoverApi
import com.example.worldcinema.data.network.requests.episodes.EpisodesApi
import com.example.worldcinema.data.network.requests.movie.MovieApi
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object AuthNetwork {
    private const val BASE_URL = "http://107684.web.hosting-russia.ru:8000/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getHttpClient(
        useCases: AuthNetworkUseCases
    ): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            addInterceptor(AuthInterceptor(useCases.getTokenFromLocalStorageUseCase))
            authenticator(
                TokenAuthenticator(
                    useCases.getTokenFromLocalStorageUseCase,
                    useCases.saveTokenToLocalStorageUseCase,
                    useCases.refreshTokenUseCase
                )
            )
        }

        return client.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getAuthRetrofit(
        useCases: AuthNetworkUseCases
    ): Retrofit {

        if (authRetrofit != null)
            return authRetrofit as Retrofit

        authRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(getHttpClient(useCases))
            .build()

        return authRetrofit as Retrofit
    }

    private var authRetrofit: Retrofit? = null

    fun getCoverApi(useCases: AuthNetworkUseCases): CoverApi =
        getAuthRetrofit(useCases).create(CoverApi::class.java)

    fun getMovieApi(useCases: AuthNetworkUseCases): MovieApi =
        getAuthRetrofit(useCases).create(MovieApi::class.java)

    fun getEpisodesApi(useCases: AuthNetworkUseCases): EpisodesApi =
        getAuthRetrofit(useCases).create(EpisodesApi::class.java)
}