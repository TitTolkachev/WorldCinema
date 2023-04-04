package com.example.worldcinema.ui.collection.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.databinding.FragmentCreateCollectionBinding

class CreateCollectionFragment : Fragment() {


    private var _binding: FragmentCreateCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CreateCollectionViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCollectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CreateCollectionViewModel::class.java]
        navController = findNavController()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}