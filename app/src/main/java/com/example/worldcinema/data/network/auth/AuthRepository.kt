package com.example.worldcinema.data.network.auth

import com.example.worldcinema.data.network.Network
import com.example.worldcinema.data.network.auth.dto.AuthCredentialDto
import com.example.worldcinema.data.network.auth.dto.RegistrationBodyDto
import com.example.worldcinema.domain.i_repository.network.IAuthRepository
import com.example.worldcinema.domain.model.AuthCredentials
import com.example.worldcinema.domain.model.RegistrationBody
import com.example.worldcinema.domain.model.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository : IAuthRepository {

    private val authApi: AuthApi = Network.getAuthApi()

    override suspend fun register(body: RegistrationBody): Flow<Result<Token>> = flow {
        try {
            val data = authApi.register(
                RegistrationBodyDto(
                    body.email,
                    body.password,
                    body.firstName,
                    body.lastName
                )
            )
            emit(Result.success(Token(data.accessToken, data.refreshToken)))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun login(body: AuthCredentials): Flow<Result<Token>> = flow {
        try {
            val data = authApi.login(AuthCredentialDto(body.email, body.password))
            emit(Result.success(Token(data.accessToken, data.refreshToken)))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}