package com.example.worldcinema.ui.helper

import android.util.Log
import com.example.worldcinema.domain.model.ChatMessage
import com.example.worldcinema.ui.model.Message
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object ChatDataToMessagesMapper {

    private fun mapMessage(m: ChatMessage): Message {
        return Message(
            m.messageId,
            LocalDateTime.parse(m.creationDateTime).plusHours(19)
                .format(DateTimeFormatter.ofPattern("dd MMMM")),
            LocalDateTime.parse(m.creationDateTime).plusHours(19)
                .format(DateTimeFormatter.ofPattern("hh:mm")),
            m.authorId ?: "",
            m.authorName,
            m.authorAvatar ?: "",
            m.text
        )
    }

    fun mapMessages(messages: List<ChatMessage>): MutableList<Message> {
        val res = mutableListOf<Message>()
        messages.forEachIndexed { index, m ->
            if (index == 0) {
                val dateTime = LocalDateTime.parse(m.creationDateTime).plusHours(19)
                res.add(createChatDateItem(dateTime))
                res.add(mapMessage(m))
            } else {
                val dateTime = LocalDateTime.parse(m.creationDateTime).plusHours(19)
                val prevItemDateTime =
                    LocalDateTime.parse(messages[index - 1].creationDateTime).plusHours(19)
                if (dateTime.format(DateTimeFormatter.ISO_DATE) != prevItemDateTime.format(
                        DateTimeFormatter.ISO_DATE
                    )
                )
                    res.add(createChatDateItem(dateTime))
                res.add(mapMessage(m))
            }
        }
        return res
    }

    private fun createChatDateItem(dateTime: LocalDateTime): Message {

        Log.e("DATE 1", dateTime.format(DateTimeFormatter.ofPattern("ddMMyyyy")))
        Log.e(
            "DATE 2", SimpleDateFormat(
                "ddMMyyyy",
                Locale.getDefault()
            ).format(Date())
        )

        return Message(
            "",
            if (dateTime.format(DateTimeFormatter.ofPattern("ddMMyyyy")) == SimpleDateFormat(
                    "ddMMyyyy",
                    Locale.getDefault()
                ).format(Date())
            )
                "Сегодня"
            else
                dateTime.format(DateTimeFormatter.ofPattern("dd MMMM")),
            "",
            "",
            "",
            "",
            ""
        )
    }
}