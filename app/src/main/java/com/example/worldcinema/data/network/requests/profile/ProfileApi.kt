package com.example.worldcinema.data.network.requests.profile

import com.example.worldcinema.data.network.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {

    @GET("api/profile")
    suspend fun getProfile(): UserDto

    @Multipart
    @POST("api/profile/avatar")
    suspend fun saveAvatar(@Part file: String)
}