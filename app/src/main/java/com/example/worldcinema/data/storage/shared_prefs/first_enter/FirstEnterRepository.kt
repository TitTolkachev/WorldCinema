package com.example.worldcinema.data.storage.shared_prefs.first_enter

import com.example.worldcinema.domain.i_repository.storage.IFirstEnterRepository

class FirstEnterRepository(private val storage: IFirstEnterStorage) :
    IFirstEnterRepository {

    override fun getFirstEnterInfo(): Boolean {
        return storage.getFirstEnterInfo()
    }

    override fun setFirstEnterInfo(value: Boolean) {
        storage.setFirstEnterInfo(value)
    }
}