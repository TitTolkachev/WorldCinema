package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.data.network.cover.CoverRepository
import com.example.worldcinema.domain.model.Cover
import kotlinx.coroutines.flow.Flow

class GetCoverUseCase(private val coverRepository: CoverRepository) {

    suspend fun execute(): Flow<Result<Cover>> {
        return coverRepository.getCover()
    }
}