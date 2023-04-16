package com.example.worldcinema.domain.i_repository.storage

import com.example.worldcinema.domain.model.CollectionIcon

interface ICollectionIconRepository {

    fun getIcons(): Result<List<CollectionIcon>>

    fun saveCollectionIcon(collectionIcon: CollectionIcon): Result<Boolean>

    fun updateCollectionIcon(collectionIcon: CollectionIcon): Result<Boolean>

    fun deleteIcons(): Result<Boolean>

    fun deleteIcon(collectionId: String): Result<Boolean>
}