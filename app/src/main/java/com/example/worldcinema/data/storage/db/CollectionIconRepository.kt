package com.example.worldcinema.data.storage.db

import com.example.worldcinema.domain.i_repository.storage.ICollectionIconRepository
import com.example.worldcinema.domain.model.CollectionIcon

class CollectionIconRepository(
    private val database: CollectionsDatabase
) : ICollectionIconRepository {

    override fun getIcons(): Result<List<CollectionIcon>> {

        return try {
            val data = database.collectionIconDao().getIcons()
                .map { CollectionIcon(it.collectionId, it.iconIndex) }
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun saveCollectionIcon(collectionIcon: CollectionIcon): Result<Boolean> {
        return try {
            database.collectionIconDao().insertCollectionIcon(
                com.example.worldcinema.data.storage.db.CollectionIcon(
                    collectionIcon.collectionId,
                    collectionIcon.iconIndex
                )
            )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun updateCollectionIcon(collectionIcon: CollectionIcon): Result<Boolean> {
        return try {
            database.collectionIconDao().updateCollectionIcon(
                com.example.worldcinema.data.storage.db.CollectionIcon(
                    collectionIcon.collectionId,
                    collectionIcon.iconIndex
                )
            )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun deleteIcons(): Result<Boolean> {
        return try {
            database.collectionIconDao().deleteIcons()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}