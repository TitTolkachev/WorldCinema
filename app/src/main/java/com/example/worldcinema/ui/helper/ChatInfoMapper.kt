package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.Chat
import com.example.worldcinema.ui.model.ChatInfo

object ChatInfoMapper {

    fun mapChatInfo(c: Chat): ChatInfo {
        return ChatInfo(c.chatId, c.chatName)
    }

    fun mapChatsInfo(chats: List<Chat>): List<ChatInfo> {
        val res = mutableListOf<ChatInfo>()
        for (c in chats)
            res.add(mapChatInfo(c))
        return res.toList()
    }
}