package com.example.worldcinema.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CollectionIconDao {

    @Insert
    fun insertCollectionIcon(collectionIcon: CollectionIcon)

    @Update
    fun updateCollectionIcon(collectionIcon: CollectionIcon)

    @Query("SELECT * FROM CollectionIcon")
    fun getIcons(): List<CollectionIcon>

    @Query("DELETE FROM CollectionIcon")
    fun deleteIcons()
}