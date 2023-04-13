package com.example.worldcinema.ui.screen.discussions.discussions.adapter

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.worldcinema.R
import com.example.worldcinema.databinding.DiscussionsItemBinding
import com.example.worldcinema.ui.model.Discussion

class DiscussionAdapter(private val discussionActionListener: IDiscussionActionListener) :
    RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder>(),
    View.OnClickListener {

    var data: MutableList<Discussion> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class DiscussionViewHolder(val binding: DiscussionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(discussion: Discussion) {
            binding.root.tag = discussion.chatId
            with(binding.discussionsItemImage) {
                Glide.with(this).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(android.R.color.transparent)
                        .error(R.drawable.default_avatar_icon)
                ).load(discussion.preview).into(this)
                clipToOutline = true
            }
            binding.discussionsItemTitle.text = discussion.movieName

            val text = discussion.authorName + ": " + discussion.text
            val span = SpannableString(text)
            span.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray_af_text
                    )
                ),
                0,
                discussion.authorName.length + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.DiscussionsItemText.text = span
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussionViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = DiscussionsItemBinding.inflate(inflater, parent, false)

        return DiscussionViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DiscussionViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.root.setOnClickListener(this@DiscussionAdapter)
    }

    override fun onClick(view: View) {
        val chatId: String = view.tag as String
        discussionActionListener.onItemClicked(chatId)
    }
}

interface IDiscussionActionListener {
    fun onItemClicked(chatId: String)
}