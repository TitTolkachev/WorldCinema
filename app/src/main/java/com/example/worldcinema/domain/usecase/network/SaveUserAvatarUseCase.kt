package com.example.worldcinema.domain.usecase.network

import android.graphics.Bitmap
import com.example.worldcinema.domain.i_repository.network.IProfileRepository
import kotlinx.coroutines.flow.Flow

class SaveUserAvatarUseCase(private val repository: IProfileRepository) {

    suspend fun execute(file: Bitmap): Flow<Result<Boolean>> {
        return repository.saveAvatar(file)
    }
}