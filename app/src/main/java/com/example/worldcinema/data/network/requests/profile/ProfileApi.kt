package com.example.worldcinema.data.network.requests.profile

import com.example.worldcinema.data.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApi {

    @GET("api/profile")
    suspend fun getProfile(): UserDto

    @POST("api/profile/avatar")
    suspend fun saveAvatar(@Body file: String)
}