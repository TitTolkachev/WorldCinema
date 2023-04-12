package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.IProfileRepository
import kotlinx.coroutines.flow.Flow

class SaveUserAvatarUseCase(private val repository: IProfileRepository) {

    suspend fun execute(file: String): Flow<Result<Boolean>> {
        return repository.saveAvatar(file)
    }
}