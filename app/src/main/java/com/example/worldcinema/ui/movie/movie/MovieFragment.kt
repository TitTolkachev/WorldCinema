package com.example.worldcinema.ui.movie.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.databinding.FragmentMovieBinding
import com.example.worldcinema.ui.movie.movie.adapter.MovieImagesAdapter

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    private lateinit var navController: NavController

    private lateinit var movieImagesAdapter: MovieImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        navController = findNavController()

        binding.imageButtonMovieArrowBack.setOnClickListener {
            activity?.finish()
        }

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {

        val linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMovieImages.layoutManager = linearLayoutManager

        movieImagesAdapter = MovieImagesAdapter()
        binding.recyclerViewMovieImages.adapter = movieImagesAdapter

        viewModel.movieImages.observe(viewLifecycleOwner) {
            if (it != null) {

                // TODO(Подставлять данные it в view адаптера)

                movieImagesAdapter.data = it
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}