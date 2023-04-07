package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IAuthRepository
import com.example.worldcinema.domain.model.RegistrationBody
import com.example.worldcinema.domain.model.Token
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: IAuthRepository) {

    suspend fun execute(body: RegistrationBody): Flow<Result<Token>> {
        return authRepository.register(body)
    }
}