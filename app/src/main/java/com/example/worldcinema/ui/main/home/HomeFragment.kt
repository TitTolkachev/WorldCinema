package com.example.worldcinema.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentHomeBinding
import com.example.worldcinema.ui.main.home.adapter.GalleryAdapter
import com.example.worldcinema.ui.main.home.adapter.IMovieActionListener

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController

    private lateinit var trendMoviesAdapter: GalleryAdapter
    private lateinit var newMoviesAdapter: GalleryAdapter
    private lateinit var recommendedMoviesAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(requireContext())
        )[HomeViewModel::class.java]
        navController = findNavController()

        initRecyclerViews()

        viewModel.coverImage.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                Glide.with(this).load(it).into(binding.imageView)
            }
        }

        binding.buttonHomeWatchMovie.setOnClickListener {
            navController.navigate(R.id.action_navigation_home_to_movieActivity)
        }

        return binding.root
    }

    private fun initRecyclerViews() {
        initTrendMoviesRecyclerView()
        initNewMoviesRecyclerView()
        initRecommendedMoviesRecyclerView()
    }

    private fun initTrendMoviesRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTrends.layoutManager = linearLayoutManager

        trendMoviesAdapter = GalleryAdapter(1, object : IMovieActionListener {
            override fun onItemClicked(movieId: String) {
                showMovie(movieId)
            }
        })
        binding.recyclerViewTrends.adapter = trendMoviesAdapter

        viewModel.trendMovies.observe(viewLifecycleOwner) {
            if (it != null) {

                // TODO(Подставлять данные it в view адаптера)

                trendMoviesAdapter.data = it
            }
        }
    }

    private fun initNewMoviesRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNewMovies.layoutManager = linearLayoutManager

        newMoviesAdapter = GalleryAdapter(2, object : IMovieActionListener {
            override fun onItemClicked(movieId: String) {
                showMovie(movieId)
            }
        })
        binding.recyclerViewNewMovies.adapter = newMoviesAdapter

        viewModel.newMovies.observe(viewLifecycleOwner) {
            if (it != null) {

                // TODO(Подставлять данные it в view адаптера)

                newMoviesAdapter.data = it
            }
        }
    }

    private fun initRecommendedMoviesRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewForYou.layoutManager = linearLayoutManager

        recommendedMoviesAdapter = GalleryAdapter(1, object : IMovieActionListener {
            override fun onItemClicked(movieId: String) {
                showMovie(movieId)
            }
        })
        binding.recyclerViewForYou.adapter = recommendedMoviesAdapter

        viewModel.recommendedMovies.observe(viewLifecycleOwner) {
            if (it != null) {

                // TODO(Подставлять данные it в view адаптера)

                recommendedMoviesAdapter.data = it
            }
        }
    }

    private fun showMovie(movieId: String) {
        navController.navigate(R.id.action_navigation_home_to_movieActivity)

        // TODO(Передавать данные при переходе)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}