package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.CollectionListItem
import com.example.worldcinema.ui.model.UsersCollection

object CollectionMapper {

    fun mapCollection(c: CollectionListItem): UsersCollection {
        return UsersCollection(c.collectionId, 0, c.name)
    }

    fun mapCollections(collections: List<CollectionListItem>): MutableList<UsersCollection> {
        val res = mutableListOf<UsersCollection>()
        for (c in collections) {
            res.add(mapCollection(c))
        }
        return res
    }
}