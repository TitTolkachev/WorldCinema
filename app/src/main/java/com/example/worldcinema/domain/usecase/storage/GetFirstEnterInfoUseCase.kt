package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.IFirstEnterRepository

class GetFirstEnterInfoUseCase(private val repository: IFirstEnterRepository) {

    fun execute(): Boolean {
        return repository.getFirstEnterInfo()
    }
}