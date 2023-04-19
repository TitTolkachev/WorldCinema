package com.example.worldcinema.ui.screen.main.home

import android.content.Intent
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
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.screen.main.home.adapter.GalleryAdapter
import com.example.worldcinema.ui.screen.main.home.adapter.IMovieActionListener
import com.example.worldcinema.ui.screen.movie.episode.EpisodeActivity

class HomeFragment : Fragment(), AlertDialog.IAlertDialogListener {

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
            HomeViewModelFactory(
                requireContext(),
                getString(R.string.collection_favourites_default_name)
            )
        )[HomeViewModel::class.java]
        navController = findNavController()

        binding.progressBarHome.visibility = View.VISIBLE
        binding.homeContent.visibility = View.GONE

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarHome.visibility = View.VISIBLE
                binding.homeContent.visibility = View.GONE
            } else {
                binding.progressBarHome.visibility = View.GONE
                binding.homeContent.visibility = View.VISIBLE
                onDataLoaded()
            }
        }

        viewModel.showAlertDialog.observe(viewLifecycleOwner) {
            if(it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
            }
        }

        return binding.root
    }

    private fun onDataLoaded() {

        initRecyclerViews()

        viewModel.coverImage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Glide.with(this).load(it).into(binding.imageView)
            }
        }

        viewModel.lastViewEpisode.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textView3.visibility = View.VISIBLE
                binding.imageButton.visibility = View.VISIBLE
                binding.textView7.visibility = View.VISIBLE
                binding.textView7.text = viewModel.lastViewMovie.value?.name ?: ""
                Glide.with(this).load(it.preview).into(binding.imageButton)
            } else {
                binding.textView3.visibility = View.GONE
                binding.imageButton.visibility = View.GONE
                binding.textView7.visibility = View.GONE
            }
        }

        binding.buttonHomeWatchMovie.setOnClickListener {
            viewModel.showAlert(AlertType.BAD_API)
        }

        binding.buttonHomeSetInterenstingTags.setOnClickListener {
            viewModel.showAlert(AlertType.BAD_FIGMA)
        }

        binding.imageButton.setOnClickListener {
            showLastViewEpisode()
        }
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
            if (it != null && it.isNotEmpty()) {
                trendMoviesAdapter.data = it
                binding.textView.visibility = View.VISIBLE
                binding.recyclerViewTrends.visibility = View.VISIBLE
            } else {
                binding.textView.visibility = View.GONE
                binding.recyclerViewTrends.visibility = View.GONE
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
            if (it != null && it.isNotEmpty()) {
                newMoviesAdapter.data = it
                binding.textView5.visibility = View.VISIBLE
                binding.recyclerViewNewMovies.visibility = View.VISIBLE
            } else {
                binding.textView5.visibility = View.GONE
                binding.recyclerViewNewMovies.visibility = View.GONE
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
            if (it != null && it.isNotEmpty()) {
                recommendedMoviesAdapter.data = it
                binding.textView6.visibility = View.VISIBLE
                binding.recyclerViewForYou.visibility = View.VISIBLE
            } else {
                binding.textView6.visibility = View.GONE
                binding.recyclerViewForYou.visibility = View.GONE
            }
        }
    }

    private fun showMovie(movieId: String) {
        val selectedMovie = viewModel.getMovie(movieId)
        if (selectedMovie != null) {
            val action =
                HomeFragmentDirections.actionNavigationHomeToMovieActivity(selectedMovie)
            navController.navigate(action)
        }
    }

    private fun showLastViewEpisode() {
        if (viewModel.lastViewMovie.value != null &&
            viewModel.lastViewEpisode.value != null &&
            viewModel.lastViewMovieEpisodesCount.value != null &&
            viewModel.lastViewMovieYears.value != null
        ) {
            val intent = Intent(requireContext(), EpisodeActivity::class.java)
            intent.putExtra(
                getString(R.string.intent_data_for_episode_movie),
                viewModel.lastViewMovie.value!!
            )
            intent.putExtra(
                getString(R.string.intent_data_for_episode_episode),
                viewModel.lastViewEpisode.value!!
            )
            intent.putExtra(
                getString(R.string.intent_data_for_episode_episodes_count),
                viewModel.lastViewMovieEpisodesCount.value!!
            )
            intent.putExtra(
                getString(R.string.intent_data_for_episode_movie_years),
                viewModel.lastViewMovieYears.value!!
            )
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistory()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun alertDialogRetry() {
        viewModel.reload()
    }

    override fun onAlertDialogDismiss() {
        viewModel.alertShowed()
    }
}