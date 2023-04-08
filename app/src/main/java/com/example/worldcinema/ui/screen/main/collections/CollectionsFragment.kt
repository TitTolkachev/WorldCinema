package com.example.worldcinema.ui.screen.main.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentCollectionsBinding
import com.example.worldcinema.ui.screen.main.collections.adapter.CollectionsAdapter
import com.example.worldcinema.ui.screen.main.collections.adapter.ICollectionActionListener

class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CollectionsViewModel
    private lateinit var navController: NavController

    private lateinit var collectionsAdapter: CollectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CollectionsViewModel::class.java]
        navController = findNavController()

        initCollectionsRecyclerView()

        binding.imageButtonAddCollection.setOnClickListener {
            navController.navigate(R.id.action_navigation_collections_to_createCollectionActivity)
        }

        viewModel.showCollection.observe(viewLifecycleOwner) {
            if(it) {
                navController.navigate(R.id.action_navigation_collections_to_moviesCollectionActivity)
                viewModel.collectionShowed()
            }
        }

        return binding.root
    }

    private fun initCollectionsRecyclerView() {

        binding.CollectionsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        collectionsAdapter = CollectionsAdapter(object : ICollectionActionListener {
            override fun onItemClicked(collectionId: String) {
                viewModel.onItemClicked(collectionId)
            }
        })
        binding.CollectionsRecyclerView.adapter = collectionsAdapter

        viewModel.collections.observe(viewLifecycleOwner) {
            if (it != null) {
                collectionsAdapter.data = it
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}