package com.example.worldcinema.ui.screen.main.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldcinema.ui.model.UsersCollection

class CollectionsViewModel : ViewModel() {

    private val _collections: MutableLiveData<MutableList<UsersCollection>> =
        MutableLiveData(mutableListOf())
    val collections: LiveData<MutableList<UsersCollection>> = _collections

    private val _showCollection: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val showCollection: LiveData<Boolean> = _showCollection

    init {
        loadData()
    }

    fun onItemClicked(collectionId: String) {
        _showCollection.value = true
    }

    fun collectionShowed() {
        _showCollection.value = false
    }

    private fun loadData() {
        _collections.value = mutableListOf(
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
            UsersCollection("1", 1, "SFsdfsFSDfsdFSdfsdfSDfsdF"),
        )
    }
}