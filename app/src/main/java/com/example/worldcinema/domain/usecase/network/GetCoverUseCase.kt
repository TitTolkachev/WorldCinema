package com.example.worldcinema.domain.usecase.network

import com.example.worldcinema.domain.i_repository.network.ICoverRepository
import com.example.worldcinema.domain.model.Cover
import kotlinx.coroutines.flow.Flow

class GetCoverUseCase(private val coverRepository: ICoverRepository) {

    suspend fun execute(): Flow<Result<Cover>> {
        return coverRepository.getCover()
    }
}