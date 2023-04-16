package com.example.worldcinema.data.storage.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CollectionIcon(
    @PrimaryKey val collectionId: String,
    val iconIndex: Int
)
