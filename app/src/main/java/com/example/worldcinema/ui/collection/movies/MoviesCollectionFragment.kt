package com.example.worldcinema.ui.collection.movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentMoviesCollectionBinding

class MoviesCollectionFragment : Fragment() {

    private var _binding: FragmentMoviesCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesCollectionViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesCollectionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MoviesCollectionViewModel::class.java]
        navController = findNavController()

        binding.imageButtonArrowBack.setOnClickListener {
            activity?.finish()
        }

        binding.imageButtonEdit.setOnClickListener {
            navController.navigate(R.id.action_moviesCollectionFragment_to_updateCollectionFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}