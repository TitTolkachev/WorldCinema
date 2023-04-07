package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.ITokenStorageRepository
import com.example.worldcinema.domain.model.Token

class SaveTokenToLocalStorageUseCase(private val tokenLocalStorageRepository: ITokenStorageRepository) {

    fun execute(token: Token) {
        tokenLocalStorageRepository.saveToken(token)
    }
}