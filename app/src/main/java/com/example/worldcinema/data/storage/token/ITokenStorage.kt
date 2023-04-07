package com.example.worldcinema.data.storage.token

import com.example.worldcinema.data.storage.model.TokenModel

interface ITokenStorage {

    fun getToken(): TokenModel?

    fun saveToken(token: TokenModel)
}