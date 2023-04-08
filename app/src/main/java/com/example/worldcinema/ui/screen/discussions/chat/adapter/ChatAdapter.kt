package com.example.worldcinema.ui.screen.discussions.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.R
import com.example.worldcinema.databinding.*
import com.example.worldcinema.ui.model.Message

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

        fun bind(message: Message, shape: ChatItemShape, bottomPadding: ChatItemPadding) {
            // TODO(Найти картинку в интернете)

            if (shape == ChatItemShape.Rounded) {
                binding.ChatDefaultMessageConstraintLayout.setBackgroundResource(R.drawable.chat_top_default_message_card_background)
            }

            if (bottomPadding == ChatItemPadding.ExtraBig) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_extra_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.Big) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.Small) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_small_padding)
                        .toInt()
                )
            }

            if(bottomPadding == ChatItemPadding.Small)
                binding.chatMessageAvatarCardView.visibility = View.INVISIBLE

            with(binding) {
                chatMessageAvatar.setImageResource(R.drawable.test_image)
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
            // TODO(Найти картинку в интернете)

            if (shape == ChatItemShape.Rounded) {
                binding.ChatUserMessageConstraintLayout.setBackgroundResource(R.drawable.chat_top_user_message_card_background)
            }

            if (bottomPadding == ChatItemPadding.ExtraBig) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_extra_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.Big) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_big_padding)
                        .toInt()
                )
            }
            if (bottomPadding == ChatItemPadding.Small) {
                binding.root.setPadding(
                    0, 0, 0,
                    binding.root.context.resources.getDimension(R.dimen.chat_message_small_padding)
                        .toInt()
                )
            }

            if(bottomPadding == ChatItemPadding.Small)
                binding.chatMessageAvatarCardView.visibility = View.INVISIBLE

            with(binding) {
                chatMessageAvatar.setImageResource(R.drawable.test_image)
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
            if (bottomPadding == ChatItemPadding.ExtraBig) {
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
            return ChatItemShape.Rounded
        if (data[position - 1].date != data[position].date)
            return ChatItemShape.Rounded
        if (data[position - 1].authorId != data[position].authorId)
            return ChatItemShape.Rounded
        return ChatItemShape.Rectangle
    }

    private fun calculateBottomItemPadding(position: Int): ChatItemPadding {
        if (position == data.size - 1)
            return ChatItemPadding.Big
        if (data[position].messageId == "")
            return ChatItemPadding.ExtraBig
        if (data[position + 1].messageId == "")
            return ChatItemPadding.ExtraBig
        if (data[position + 1].authorId != data[position].authorId)
            return ChatItemPadding.Big
        return ChatItemPadding.Small
    }
}

enum class ChatItemShape {
    Rounded,
    Rectangle
}

enum class ChatItemPadding {
    Small,
    Big,
    ExtraBig
}