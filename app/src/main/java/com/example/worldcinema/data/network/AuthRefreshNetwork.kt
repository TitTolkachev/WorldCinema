package com.example.worldcinema.data.network

import com.example.worldcinema.data.network.requests.auth.AuthRefreshApi
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object AuthRefreshNetwork {
    private const val BASE_URL = "http://107684.web.hosting-russia.ru:8000/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getHttpClient(getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            addInterceptor(AuthRefreshInterceptor(getTokenFromLocalStorageUseCase))
        }

        return client.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getAuthRetrofit(getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase): Retrofit {

        if (authRetrofit != null)
            return authRetrofit as Retrofit

        authRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(getHttpClient(getTokenFromLocalStorageUseCase))
            .build()

        return authRetrofit as Retrofit
    }

    private var authRetrofit: Retrofit? = null

    fun getAuthRefreshApi(getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase): AuthRefreshApi =
        getAuthRetrofit(getTokenFromLocalStorageUseCase).create(AuthRefreshApi::class.java)
}