package com.example.worldcinema.ui.screen.discussions.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.databinding.ActivityChatBinding
import com.example.worldcinema.ui.screen.discussions.chat.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel

    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
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
}