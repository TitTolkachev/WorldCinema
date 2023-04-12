package com.example.worldcinema.data.network.requests.profile

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.domain.i_repository.network.IProfileRepository
import com.example.worldcinema.domain.model.User
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepository(useCases: AuthNetworkUseCases) : IProfileRepository {

    private val api = AuthNetwork.getProfileApi(useCases)

    override suspend fun getProfile(): Flow<Result<User>> = flow {
        try {
            val data = api.getProfile()
            emit(
                Result.success(
                    User(
                        data.userId,
                        data.firstName,
                        data.lastName,
                        data.email,
                        data.avatar
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }

    override suspend fun saveAvatar(file: String): Flow<Result<Boolean>> = flow {
        try {
            api.saveAvatar(file)
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }
}