package com.example.worldcinema.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.databinding.FragmentHomeBinding
import com.example.worldcinema.ui.home.adapter.GalleryAdapter
import com.example.worldcinema.ui.home.adapter.IMovieActionListener

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var trendMoviesAdapter: GalleryAdapter
    private lateinit var newMoviesAdapter: GalleryAdapter
    private lateinit var recommendedMoviesAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        initRecyclerViews()

        return root
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

        // Подписываем adapter на view model
        viewModel.trendMovies.observe(viewLifecycleOwner) {
            if (it != null) {

                // TODO(Подставлять данные it в view адаптера)

                trendMoviesAdapter.data = it
            }
        }
    }

    private fun initNewMoviesRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNewMovies.layoutManager = linearLayoutManager

        newMoviesAdapter = GalleryAdapter(2, object : IMovieActionListener {
            override fun onItemClicked(movieId: String) {
                showMovie(movieId)
            }
        })
        binding.recyclerViewNewMovies.adapter = newMoviesAdapter

        // Подписываем adapter на view model
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

        // Подписываем adapter на view model
        viewModel.recommendedMovies.observe(viewLifecycleOwner) {
            if (it != null) {

                // TODO(Подставлять данные it в view адаптера)

                recommendedMoviesAdapter.data = it
            }
        }
    }

    private fun showMovie(movieId: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}