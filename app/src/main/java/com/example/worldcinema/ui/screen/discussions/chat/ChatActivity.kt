package com.example.worldcinema.ui.screen.discussions.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityChatBinding
import com.example.worldcinema.ui.screen.discussions.chat.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel

    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            ChatViewModelFactory(
                this,
                intent.getStringExtra(getString(R.string.intent_data_for_chat_chat_id)) ?: ""
            )
        )[ChatViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        binding.imageButtonSendMessage.setOnClickListener {
            if (binding.chatMessageInput.text.toString().isNotEmpty()) {
                viewModel.sendMessage(binding.chatMessageInput.text.toString())
                binding.chatMessageInput.setText("")
            }
        }

        viewModel.chatData.observe(this) {
            viewModel.newData()
        }

        viewModel.chatName.observe(this) {
            binding.textViewChatTitle.text = it
        }

        initChatAdapter()

        setContentView(binding.root)
    }

    private fun initChatAdapter() {

        val adapterLayoutManager = LinearLayoutManager(binding.root.context)
        binding.RecyclerViewChat.layoutManager = adapterLayoutManager.apply {
            // reverseLayout = true
            stackFromEnd = true
        }
        adapter = ChatAdapter()
        binding.RecyclerViewChat.adapter = adapter

        viewModel.messages.observe(this) {
            if (it != null) {
                adapter.data = it
            }
        }

        viewModel.userId.observe(this) {
            if (it != null) {
                adapter.userId = it
            }
        }
    }

    override fun onDestroy() {
        viewModel.onViewDestroyed()
        super.onDestroy()
    }
}