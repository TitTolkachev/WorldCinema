package com.example.worldcinema.ui.discussions.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.R
import com.example.worldcinema.databinding.*
import com.example.worldcinema.ui.discussions.chat.model.Message
import java.util.Date

class ChatAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: MutableList<Message> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    var userId: String = ""
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class DefaultMessageViewHolder(val binding: ChatDefaultMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, position: Int) {
            // TODO(Найти картинку в интернете)

            with(binding) {
                chatMessageAvatar.setImageResource(R.drawable.test_image)
                textViewDefaultMessageText.text = message.text
                "${message.authorName} • ${message.creationDateTime}".also { textViewDefaultMessageInfo.text = it }
            }
        }
    }

    class UserMessageViewHolder(val binding: ChatUserMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, position: Int) {
            // TODO(Найти картинку в интернете)

            with(binding) {
                chatMessageAvatar.setImageResource(R.drawable.test_image)
                textViewUserMessageText.text = message.text
                "${message.authorName} • ${message.creationDateTime}".also { textViewUserMessageInfo.text = it }
            }
        }
    }

    class DateViewHolder(val binding: ChatDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: Date, position: Int) {
            // TODO(Сделать нормально)
            binding.textViewMessageDate.text = date.day.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (data[position].authorId == "")
            return 3
        if (userId == data[position].authorId)
            return 2
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            1 -> {
                val binding = ChatDefaultMessageItemBinding.inflate(inflater, parent, false)

                DefaultMessageViewHolder(binding)
            }
            2 -> {
                val binding = ChatUserMessageItemBinding.inflate(inflater, parent, false)

                UserMessageViewHolder(binding)
            }
            else -> {
                val binding = ChatDateItemBinding.inflate(inflater, parent, false)

                DateViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            1 -> {
                val viewHolder = holder as DefaultMessageViewHolder
                viewHolder.bind(data[position], position)
            }
            2 -> {
                val viewHolder = holder as UserMessageViewHolder
                viewHolder.bind(data[position], position)
            }
            3 -> {
                val viewHolder = holder as DateViewHolder
                viewHolder.bind(data[position].creationDateTime, position)
            }
        }
    }
}