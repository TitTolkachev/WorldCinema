package com.example.worldcinema.ui.screen.discussions.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.worldcinema.R
import com.example.worldcinema.databinding.*
import com.example.worldcinema.ui.model.Message

class ChatAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Message> = mutableListOf()
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

        fun bind(message: Message, shape: ChatItemShape, bottomPadding: ChatItemPadding) {

            binding.chatMessageAvatarCardView.visibility = View.VISIBLE
            binding.chatMessageCardView.visibility = View.VISIBLE
            binding.chatMessageBackground.visibility = View.VISIBLE

            if (shape == ChatItemShape.ROUNDED) {
                binding.chatMessageBackground.setBackgroundResource(R.drawable.chat_top_default_message_card_background)
            }

            if (bottomPadding == ChatItemPadding.EXTRA_BIG) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_extra_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.BIG) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.SMALL) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_small_padding)
                        .toInt()
                )
            }

            if (bottomPadding == ChatItemPadding.SMALL)
                binding.chatMessageAvatarCardView.visibility = View.INVISIBLE
            else
                with(binding) {
                    Glide.with(chatMessageAvatar).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(android.R.color.transparent)
                            .error(R.drawable.default_avatar_icon)
                    ).load(message.authorAvatar).into(chatMessageAvatar)
                }

            with(binding) {
                textViewDefaultMessageText.text = message.text
                "${message.authorName} • ${message.time}".also {
                    textViewDefaultMessageInfo.text = it
                }
            }
        }
    }

    class UserMessageViewHolder(val binding: ChatUserMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, shape: ChatItemShape, bottomPadding: ChatItemPadding) {

            binding.chatMessageAvatarCardView.visibility = View.VISIBLE
            binding.chatMessageCardView.visibility = View.VISIBLE
            binding.chatMessageBackground.visibility = View.VISIBLE

            if (shape == ChatItemShape.ROUNDED) {
                binding.chatMessageBackground.setBackgroundResource(R.drawable.chat_top_user_message_card_background)
            }

            if (bottomPadding == ChatItemPadding.EXTRA_BIG) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_extra_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.BIG) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.SMALL) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_small_padding)
                        .toInt()
                )
            }

            if (bottomPadding == ChatItemPadding.SMALL)
                binding.chatMessageAvatarCardView.visibility = View.INVISIBLE
            else
                with(binding) {
                    Glide.with(chatMessageAvatar).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(android.R.color.transparent)
                            .error(R.drawable.default_avatar_icon)
                    ).load(message.authorAvatar).into(chatMessageAvatar)
                }

            with(binding) {
                textViewUserMessageText.text = message.text
                "${message.authorName} • ${message.time}".also {
                    textViewUserMessageInfo.text = it
                }
            }
        }
    }

    class DateViewHolder(val binding: ChatDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String, bottomPadding: ChatItemPadding) {
            if (bottomPadding == ChatItemPadding.EXTRA_BIG) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_extra_big_padding)
                        .toInt()
                )
            }
            binding.textViewMessageDate.text = date
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
                val shape = calculateItemShape(position)
                val bottomPadding = calculateBottomItemPadding(position)
                viewHolder.bind(data[position], shape, bottomPadding)
            }
            2 -> {
                val viewHolder = holder as UserMessageViewHolder
                val shape = calculateItemShape(position)
                val bottomPadding = calculateBottomItemPadding(position)
                viewHolder.bind(data[position], shape, bottomPadding)
            }
            3 -> {
                val viewHolder = holder as DateViewHolder
                val bottomPadding = calculateBottomItemPadding(position)
                viewHolder.bind(data[position].date, bottomPadding)
            }
        }
    }

    private fun calculateItemShape(position: Int): ChatItemShape {
        if (position == 0)
            return ChatItemShape.ROUNDED
        if (data[position - 1].date != data[position].date)
            return ChatItemShape.ROUNDED
        if (data[position - 1].authorId != data[position].authorId)
            return ChatItemShape.ROUNDED
        return ChatItemShape.RECTANGLE
    }

    private fun calculateBottomItemPadding(position: Int): ChatItemPadding {
        if (position == data.size - 1)
            return ChatItemPadding.BIG
        if (data[position].messageId == "")
            return ChatItemPadding.EXTRA_BIG
        if (data[position + 1].messageId == "")
            return ChatItemPadding.EXTRA_BIG
        if (data[position + 1].authorId != data[position].authorId)
            return ChatItemPadding.BIG
        return ChatItemPadding.SMALL
    }
}

enum class ChatItemShape {
    ROUNDED,
    RECTANGLE
}

enum class ChatItemPadding {
    SMALL,
    BIG,
    EXTRA_BIG
}