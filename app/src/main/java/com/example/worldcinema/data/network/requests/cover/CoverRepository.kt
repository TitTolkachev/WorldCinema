package com.example.worldcinema.data.network.requests.cover

import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.domain.i_repository.network.ICoverRepository
import com.example.worldcinema.domain.model.Cover
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CoverRepository(useCases: AuthNetworkUseCases) : ICoverRepository {

    private val api = AuthNetwork.getCoverApi(useCases)

    override suspend fun getCover(): Flow<Result<Cover>> = flow {
        try {
            val data = api.getCover()
            emit(Result.success(Cover(data.backgroundImage, data.foregroundImage)))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }.flowOn(Dispatchers.IO)
}