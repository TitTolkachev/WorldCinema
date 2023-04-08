package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface IAuthRefreshRepository {

    suspend fun refreshToken(): Flow<Result<Token>>
}