package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IAuthRepository
import com.example.worldcinema.domain.model.AuthCredentials
import com.example.worldcinema.domain.model.Token
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: IAuthRepository) {

    suspend fun execute(credentials: AuthCredentials): Flow<Result<Token>> {
        return authRepository.login(credentials)
    }
}