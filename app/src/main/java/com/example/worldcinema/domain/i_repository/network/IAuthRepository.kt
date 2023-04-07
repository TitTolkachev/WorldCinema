package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.AuthCredentials
import com.example.worldcinema.domain.model.RegistrationBody
import com.example.worldcinema.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun register(body: RegistrationBody): Flow<Result<Token>>

    suspend fun login(body: AuthCredentials): Flow<Result<Token>>
}