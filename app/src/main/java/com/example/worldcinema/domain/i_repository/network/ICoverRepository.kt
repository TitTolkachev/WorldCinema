package com.example.worldcinema.domain.i_repository.network

import com.example.worldcinema.domain.model.Cover
import kotlinx.coroutines.flow.Flow

interface ICoverRepository {

    suspend fun getCover(): Flow<Result<Cover>>
}