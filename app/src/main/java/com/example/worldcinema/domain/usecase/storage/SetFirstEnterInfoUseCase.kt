package com.example.worldcinema.domain.usecase.storage

import com.example.worldcinema.domain.i_repository.storage.IFirstEnterRepository

class SetFirstEnterInfoUseCase(private val repository: IFirstEnterRepository) {

    fun execute(value: Boolean) {
        repository.setFirstEnterInfo(value)
    }
}