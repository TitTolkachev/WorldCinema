package com.example.worldcinema.domain.i_repository.storage

interface IFirstEnterRepository {

    fun getFirstEnterInfo(): Boolean

    fun setFirstEnterInfo(value: Boolean)
}