package com.example.worldcinema.ui.screen.discussions.discussions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityDiscussionsBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.discussions.chat.ChatActivity
import com.example.worldcinema.ui.screen.discussions.discussions.adapter.DiscussionAdapter
import com.example.worldcinema.ui.screen.discussions.discussions.adapter.IDiscussionActionListener

class DiscussionsActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener {

    private lateinit var binding: ActivityDiscussionsBinding
    private lateinit var viewModel: DiscussionsViewModel

    private lateinit var adapter: DiscussionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiscussionsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            DiscussionsViewModelFactory(this)
        )[DiscussionsViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        viewModel.showChat.observe(this) {
            if (it) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra(
                    getString(R.string.intent_data_for_chat_chat_id),
                    viewModel.selectedChatId.value
                )
                startActivity(intent)
                viewModel.chatShowed()
            }
        }

        initDiscussionsAdapter()

        setContentView(binding.root)
    }

    private fun initDiscussionsAdapter() {

        binding.RecyclerViewDiscussions.layoutManager = LinearLayoutManager(this)
        adapter = DiscussionAdapter(object : IDiscussionActionListener {
            override fun onItemClicked(chatId: String) {
                viewModel.onItemClicked(chatId)
            }
        })
        binding.RecyclerViewDiscussions.adapter = adapter

        viewModel.discussions.observe(this) {
            if (it != null) {
                adapter.data = it
            }
        }
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
}