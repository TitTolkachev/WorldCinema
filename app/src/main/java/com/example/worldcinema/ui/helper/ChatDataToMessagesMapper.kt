package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.ChatMessage
import com.example.worldcinema.ui.model.Message

object ChatDataToMessagesMapper {

    private fun mapMessage(m: ChatMessage): Message {
        return Message(
            m.messageId,
            m.creationDateTime,
            m.creationDateTime,
            m.authorId ?: "",
            m.authorName,
            m.authorAvatar ?: "",
            m.text
        )
    }

    fun mapMessages(messages: List<ChatMessage>): MutableList<Message> {
        val res = mutableListOf<Message>()
        for (m in messages)
            res.add(mapMessage(m))
        return res
    }
}