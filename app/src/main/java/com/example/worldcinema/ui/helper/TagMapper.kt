package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.Tag
import com.example.worldcinema.ui.model.MovieTag

object TagMapper {

    fun mapTag(t: Tag): MovieTag {
        return MovieTag(t.tagId, t.tagName, t.categoryName)
    }

    fun mapTags(tags: List<Tag>): List<MovieTag> {
        val res = mutableListOf<MovieTag>()
        for (t in tags)
            res.add(mapTag(t))
        return res.toList()
    }
}