package com.example.worldcinema.data.storage.shared_prefs.first_enter

interface IFirstEnterStorage {

    fun getFirstEnterInfo(): Boolean

    fun setFirstEnterInfo(value: Boolean)
}