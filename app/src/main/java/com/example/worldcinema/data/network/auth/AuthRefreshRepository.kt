package com.example.worldcinema.data.network.auth

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.domain.i_repository.network.IAuthRefreshRepository
import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import com.example.worldcinema.domain.usecase.network.RefreshTokenUseCase
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRefreshRepository(
    getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase,
    saveTokenToLocalStorageUseCase: SaveTokenToLocalStorageUseCase
) : IAuthRefreshRepository {

    private val authRefreshApi: AuthRefreshApi = AuthNetwork.getAuthRefreshApi(
        AuthNetworkUseCases(
            getTokenFromLocalStorageUseCase, saveTokenToLocalStorageUseCase,
            RefreshTokenUseCase(this)
        )
    )

    override suspend fun refreshToken(refreshToken: String): Flow<Result<Token>> = flow {
        try {
            val data = authRefreshApi.refresh()
            emit(Result.success(Token(data.accessToken, data.refreshToken)))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}