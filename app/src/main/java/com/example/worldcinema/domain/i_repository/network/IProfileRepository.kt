package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {

    suspend fun getProfile(): Flow<Result<User>>

    suspend fun saveAvatar(file: String): Flow<Result<Boolean>>
}