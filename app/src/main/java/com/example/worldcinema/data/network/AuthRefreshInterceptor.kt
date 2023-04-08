package com.example.worldcinema.data.network

import com.example.worldcinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthRefreshInterceptor(
    private val getTokenFromLocalStorageUseCase: GetTokenFromLocalStorageUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val builder = request.newBuilder()

        val refreshToken = getTokenFromLocalStorageUseCase.execute()?.refreshToken ?: ""
        builder.addHeader("Authorization", "Bearer $refreshToken")

        return chain.proceed(builder.build())
    }
}