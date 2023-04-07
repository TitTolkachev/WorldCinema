package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.ITokenStorageRepository
import com.example.worldcinema.domain.model.Token

class GetTokenFromLocalStorageUseCase(private val tokenLocalStorageRepository: ITokenStorageRepository) {

    fun execute(): Token? {
        return tokenLocalStorageRepository.getToken()
    }
}