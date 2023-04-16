package com.example.worldcinema.data.storage.shared_prefs.token

import com.example.worldcinema.data.storage.shared_prefs.model.TokenModel

interface ITokenStorage {

    fun getToken(): TokenModel?

    fun saveToken(token: TokenModel)
}