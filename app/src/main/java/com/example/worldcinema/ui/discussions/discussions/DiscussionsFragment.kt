package com.example.worldcinema.ui.discussions.discussions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentDiscussionsBinding
import com.example.worldcinema.ui.discussions.discussions.adapter.DiscussionAdapter
import com.example.worldcinema.ui.discussions.discussions.adapter.IDiscussionActionListener

class DiscussionsFragment : Fragment() {

    private var _binding: FragmentDiscussionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DiscussionsViewModel
    private lateinit var navController: NavController

    private lateinit var adapter: DiscussionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscussionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DiscussionsViewModel::class.java]
        navController = findNavController()

        binding.imageButtonArrowBack.setOnClickListener {
            activity?.finish()
        }

        viewModel.showChat.observe(viewLifecycleOwner) {
            if(it) {
                navController.navigate(R.id.action_discussionsFragment_to_chatFragment)
            }
        }

        initDiscussionsAdapter()

        return binding.root
    }

    private fun initDiscussionsAdapter() {

        binding.RecyclerViewDiscussions.layoutManager = LinearLayoutManager(binding.root.context)
        adapter = DiscussionAdapter(object : IDiscussionActionListener {
            override fun onItemClicked(chatId: String) {
                viewModel.onItemClicked(chatId)
            }
        })
        binding.RecyclerViewDiscussions.adapter = adapter

        viewModel.discussions.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.data = it
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}