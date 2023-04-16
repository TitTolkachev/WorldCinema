package com.example.worldcinema.data.storage.shared_prefs.token

import com.example.worldcinema.data.storage.shared_prefs.model.TokenModel
import com.example.worldcinema.domain.i_repository.storage.ITokenStorageRepository
import com.example.worldcinema.domain.model.Token

class TokenStorageRepository(private val tokenStorage: ITokenStorage) : ITokenStorageRepository {

    override fun getToken(): Token? {
        val data = tokenStorage.getToken()
        return if (data != null)
            Token(data.accessToken, data.refreshToken)
        else null
    }

    override fun saveToken(token: Token) {
        tokenStorage.saveToken(TokenModel(token.accessToken, token.refreshToken))
    }
}