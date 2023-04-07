package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IAuthRefreshRepository
import com.example.worldcinema.domain.model.Token
import kotlinx.coroutines.flow.Flow

class RefreshTokenUseCase(private val repository: IAuthRefreshRepository) {

    suspend fun execute(refreshToken: String): Flow<Result<Token>> {
        return repository.refreshToken(refreshToken)
    }
}