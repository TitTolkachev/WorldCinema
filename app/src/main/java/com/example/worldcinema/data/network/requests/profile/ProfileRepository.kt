package com.example.worldcinema.data.network.requests.profile

import android.graphics.Bitmap
import com.example.worldcinema.data.network.AuthNetwork
import com.example.worldcinema.domain.i_repository.network.IProfileRepository
import com.example.worldcinema.domain.model.User
import com.example.worldcinema.domain.usecase.model.AuthNetworkUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ProfileRepository(useCases: AuthNetworkUseCases) : IProfileRepository {

    private val api = AuthNetwork.getProfileApi(useCases)

    override suspend fun getProfile(): Flow<Result<User>> = flow {
        try {
            val data = api.getProfile()
            emit(
                Result.success(
                    User(
                        data.userId,
                        data.firstName,
                        data.lastName,
                        data.email,
                        data.avatar
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }

    override suspend fun saveAvatar(file: Bitmap): Flow<Result<Boolean>> = flow {
        try {
            api.saveAvatar(convertImageFileToRequestBody(file))
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(Throwable(e)))
        }
    }

    private fun convertImageFileToRequestBody(file: Bitmap): MultipartBody.Part {

        val stream = ByteArrayOutputStream()
        file.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        return MultipartBody.Part.createFormData(
            "photo[content]", "photo",
            byteArray.toRequestBody("image/png".toMediaTypeOrNull(), 0, byteArray.size)
        )
    }
}