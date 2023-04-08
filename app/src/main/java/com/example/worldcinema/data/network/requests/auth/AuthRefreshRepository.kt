package com.example.worldcinema.data.network.requests.auth

import com.example.worldcinema.data.network.AuthRefreshNetwork
import com.example.worldcinema.domain.i_repository.network.IAuthRefreshRepository
import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRefreshRepository(
    getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
) : IAuthRefreshRepository {

    private val authRefreshApi: AuthRefreshApi = AuthRefreshNetwork.getAuthRefreshApi(
        getTokenFromLocalStorageUseCase
    )

    override suspend fun refreshToken(): Flow<Result<Token>> = flow {
        try {
            val data = authRefreshApi.refresh()
            emit(Result.success(Token(data.accessToken, data.refreshToken)))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}