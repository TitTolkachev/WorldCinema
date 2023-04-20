package com.example.worldcinema.ui.screen.discussions.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityChatBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.discussions.chat.adapter.ChatAdapter

class ChatActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener,
    AlertDialog.IAlertDialogListener {

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

        viewModel.chatName.observe(this) {
            binding.textViewChatTitle.text = it
        }

        viewModel.chatData.observe(this) {
            if (it != null) {
                viewModel.newData()
                onDataLoaded()
            }
        }

        viewModel.showAlertDialog.observe(this) {
            if (it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
            }
        }

        setContentView(binding.root)
    }

    private fun onDataLoaded() {

        binding.imageButtonSendMessage.setOnClickListener {
            if (binding.chatMessageInput.text.toString().isNotEmpty()) {
                viewModel.sendMessage(binding.chatMessageInput.text.toString())
                binding.chatMessageInput.setText("")
            }
        }

        initChatAdapter()
    }

    private fun initChatAdapter() {

        val adapterLayoutManager = LinearLayoutManager(binding.root.context)
        binding.RecyclerViewChat.layoutManager = adapterLayoutManager.apply {
            stackFromEnd = true
        }
        adapter = ChatAdapter()
        binding.RecyclerViewChat.adapter = adapter

        viewModel.messages.observe(this) {
            if (it != null) {
                adapter.data = it
                binding.RecyclerViewChat.scrollToPosition(-1)
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

    override fun alertDialogExit() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    override fun alertDialogRetry() {
        viewModel.reload()
    }

    override fun onAlertDialogDismiss() {
        viewModel.alertShowed()
    }
}