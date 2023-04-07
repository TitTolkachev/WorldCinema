package com.example.worldcinema.domain.i_repository.storage

import com.example.worldcinema.domain.model.Token

interface ITokenStorageRepository {

    fun getToken(): Token?

    fun saveToken(token: Token)
}