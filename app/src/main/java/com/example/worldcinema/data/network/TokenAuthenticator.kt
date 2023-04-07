package com.example.worldcinema.data.network

import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    private val saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        val localToken = getTokenFromLocalStorageUseCase.execute()

        if (localToken != null) {

            var remoteToken: Token? = null
            runBlocking {
                refreshTokenUseCase.execute(localToken.refreshToken).collect { result ->
                    result.onSuccess {
                        remoteToken = it
                    }
                }
            }

            saveTokenToLocalStorageUseCase.execute(
                Token(
                    remoteToken?.accessToken ?: "",
                    remoteToken?.refreshToken ?: ""
                )
            )

            return if (response.responseCount >= 1) {
                null
            } else {
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${remoteToken?.accessToken ?: ""}")
                    .build()
            }
        }
        return null
    }

    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()
}