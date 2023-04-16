package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.CollectionIcon
import com.example.worldcinema.domain.model.CollectionListItem
import com.example.worldcinema.ui.model.UsersCollection

object CollectionMapper {

    private fun mapCollection(c: CollectionListItem, icons: List<CollectionIcon>): UsersCollection {
        return UsersCollection(
            c.collectionId,
            icons.singleOrNull { i -> i.collectionId == c.collectionId }?.iconIndex ?: 0,
            c.name
        )
    }

    fun mapCollections(
        collections: List<CollectionListItem>,
        icons: List<CollectionIcon>
    ): MutableList<UsersCollection> {
        val res = mutableListOf<UsersCollection>()
        for (c in collections) {
            res.add(mapCollection(c, icons))
        }
        return res
    }
}