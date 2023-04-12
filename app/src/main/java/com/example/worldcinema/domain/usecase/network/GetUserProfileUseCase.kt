package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IProfileRepository
import com.example.worldcinema.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetUserProfileUseCase(private val repository: IProfileRepository) {

    suspend fun execute(): Flow<Result<User>> {
        return repository.getProfile()
    }
}