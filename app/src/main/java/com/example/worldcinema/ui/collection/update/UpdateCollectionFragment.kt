package com.example.worldcinema.ui.collection.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.databinding.FragmentUpdateCollectionBinding

class UpdateCollectionFragment : Fragment() {

    private var _binding: FragmentUpdateCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UpdateCollectionViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateCollectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UpdateCollectionViewModel::class.java]
        navController = findNavController()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}