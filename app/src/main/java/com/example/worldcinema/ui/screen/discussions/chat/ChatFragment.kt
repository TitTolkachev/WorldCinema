package com.example.worldcinema.ui.screen.discussions.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.databinding.FragmentChatBinding
import com.example.worldcinema.ui.screen.discussions.chat.adapter.ChatAdapter

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatViewModel
    private lateinit var navController: NavController

    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        navController = findNavController()

        binding.imageButtonArrowBack.setOnClickListener {
            navController.popBackStack()
        }

        initChatAdapter()

        return binding.root
    }

    private fun initChatAdapter() {

        val adapterLayoutManager = LinearLayoutManager(binding.root.context)
        binding.RecyclerViewChat.layoutManager = adapterLayoutManager.apply {
            // reverseLayout = true
            stackFromEnd = true
        }
        adapter = ChatAdapter()
        binding.RecyclerViewChat.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.data = it
            }
        }

        viewModel.userId.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.userId = it
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}