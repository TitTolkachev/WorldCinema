package com.example.worldcinema.ui.helper

import com.example.worldcinema.domain.model.ChatMessage
import com.example.worldcinema.ui.model.Message
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

object ChatDataToMessagesMapper {

    private val zoneOffset: Long = getZoneOffset()

    private fun getZoneOffset(): Long {
        val mCalendar: Calendar = GregorianCalendar()
        val mTimeZone = mCalendar.timeZone
        val mGMTOffset = mTimeZone.rawOffset.toLong()
        return TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS)
    }

    private fun mapMessage(m: ChatMessage): Message {
        val dt = LocalDateTime.parse(m.creationDateTime).plusHours(zoneOffset)
        return Message(
            m.messageId,
            dt.format(DateTimeFormatter.ofPattern("dd MMMM")),
            dt.format(DateTimeFormatter.ofPattern("HH:mm")),
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
                val dt = LocalDateTime.parse(m.creationDateTime).plusHours(zoneOffset)
                res.add(createChatDateItem(dt))
                res.add(mapMessage(m))
            } else {
                val dt = LocalDateTime.parse(m.creationDateTime).plusHours(zoneOffset)
                val prevItemDateTime =
                    LocalDateTime.parse(messages[index - 1].creationDateTime).plusHours(zoneOffset)
                if (dt.format(DateTimeFormatter.ISO_DATE) != prevItemDateTime.format(
                        DateTimeFormatter.ISO_DATE
                    )
                )
                    res.add(createChatDateItem(dt))
                res.add(mapMessage(m))
            }
        }
        return res
    }

    private fun createChatDateItem(dateTime: LocalDateTime): Message {

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