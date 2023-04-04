package com.example.worldcinema.ui.collection.icon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.databinding.FragmentIconCollectionBinding

class IconCollectionFragment : Fragment() {

    private var _binding: FragmentIconCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: IconCollectionViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIconCollectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[IconCollectionViewModel::class.java]
        navController = findNavController()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}