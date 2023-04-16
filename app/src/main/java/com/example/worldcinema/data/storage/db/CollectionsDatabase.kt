package com.example.worldcinema.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class CollectionsDatabase : RoomDatabase() {
    abstract fun collectionIconDao(): CollectionIconDao
}