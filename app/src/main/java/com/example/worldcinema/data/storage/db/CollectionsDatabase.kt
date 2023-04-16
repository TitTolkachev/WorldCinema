package com.example.worldcinema.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CollectionIcon::class], version = 1)
abstract class CollectionsDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "collections_db"
    }

    abstract fun collectionIconDao(): CollectionIconDao
}