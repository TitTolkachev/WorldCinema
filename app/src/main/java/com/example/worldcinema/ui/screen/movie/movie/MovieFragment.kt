package com.example.worldcinema.ui.screen.movie.movie

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentMovieBinding
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.screen.discussions.chat.ChatActivity
import com.example.worldcinema.ui.screen.movie.movie.adapter.IMovieEpisodeActionListener
import com.example.worldcinema.ui.screen.movie.movie.adapter.MovieEpisodesAdapter
import com.example.worldcinema.ui.screen.movie.movie.adapter.MovieImagesAdapter

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    private lateinit var navController: NavController

    // Intent Data
    private var movieData: Movie? = null
    private var movieId: String? = null
    private var collectionId: String? = null

    private lateinit var movieImagesAdapter: MovieImagesAdapter
    private lateinit var movieEpisodesAdapter: MovieEpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        getIntentData()
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(
                requireContext(),
                movieId,
                collectionId,
                movieData
            )
        )[MovieViewModel::class.java]
        navController = findNavController()

        binding.imageButtonMovieArrowBack.setOnClickListener {
            activity?.finish()
        }

        viewModel.isMovieDataLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.movieContent.visibility = View.GONE
                binding.progressBarMovie.visibility = View.VISIBLE
            } else {
                binding.movieContent.visibility = View.VISIBLE
                binding.progressBarMovie.visibility = View.GONE
                onMovieDataLoaded(inflater)
            }
        }

        return binding.root
    }

    private fun onMovieDataLoaded(inflater: LayoutInflater) {

        binding.imageButtonMovieChat.setOnClickListener {
            val intent = Intent(view?.context, ChatActivity::class.java)
            intent.putExtra(
                getString(R.string.intent_data_for_chat_chat_id),
                viewModel.movie.value?.chatInfo?.chatId ?: ""
            )
            startActivity(intent)
        }

        viewModel.isEpisodesDataLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarMovieEpisodes.visibility = View.VISIBLE
                binding.textViewMovieEpisodesTitle.visibility = View.GONE
                binding.RecyclerViewMovieEpisodes.visibility = View.GONE
            } else {
                if (viewModel.movieEpisodes.value!!.isEmpty()) {
                    binding.progressBarMovieEpisodes.visibility = View.GONE
                    binding.textViewMovieEpisodesTitle.visibility = View.GONE
                    binding.RecyclerViewMovieEpisodes.visibility = View.GONE
                    initEpisodesRecyclerView()
                } else {
                    binding.progressBarMovieEpisodes.visibility = View.GONE
                    binding.textViewMovieEpisodesTitle.visibility = View.VISIBLE
                    binding.RecyclerViewMovieEpisodes.visibility = View.VISIBLE
                    initEpisodesRecyclerView()
                }
            }
        }

        viewModel.movieAge.observe(viewLifecycleOwner) {
            if (it != null) {
                with(binding.textViewMovieAge) {
                    text = it
                    setTextColor(getColor(context, viewModel.getMovieAgeColor()))
                }
            }
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            binding.FlexboxMovieTags.removeAllViews()
            if (it != null) {
                val tags = it.tags
                for (t in tags) {
                    inflater.inflate(R.layout.movie_tag_item, binding.FlexboxMovieTags)
                    val view = binding.FlexboxMovieTags[binding.FlexboxMovieTags.childCount - 1]
                    view.id = View.generateViewId()
                    (view as Button).text = t.tagName
                }

                Glide.with(binding.imageViewMovie).load(it.poster).into(binding.imageViewMovie)

                binding.textViewMovieDescription.text = it.description
            }
        }

        initImagesRecyclerView()
    }

    private fun initImagesRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.RecyclerViewMovieImages.layoutManager = linearLayoutManager

        movieImagesAdapter = MovieImagesAdapter()
        binding.RecyclerViewMovieImages.adapter = movieImagesAdapter

        viewModel.movieImages.observe(viewLifecycleOwner) {
            if (it != null) {
                movieImagesAdapter.data = it
            }
        }
    }

    private fun initEpisodesRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        binding.RecyclerViewMovieEpisodes.layoutManager = linearLayoutManager

        movieEpisodesAdapter = MovieEpisodesAdapter(object : IMovieEpisodeActionListener {
            override fun onItemClicked(episodeId: String) {
                val episode = viewModel.getEpisode(episodeId)
                if (episode != null && viewModel.movie.value != null) {
                    val action = MovieFragmentDirections.actionMovieFragmentToEpisodeFragment(
                        viewModel.movie.value!!,
                        episode,
                        viewModel.getEpisodesCount(),
                        viewModel.getMovieYears()
                    )
                    navController.navigate(action)
                }
            }
        })
        binding.RecyclerViewMovieEpisodes.adapter = movieEpisodesAdapter

        viewModel.movieEpisodes.observe(viewLifecycleOwner) {
            if (it != null)
                movieEpisodesAdapter.data = it
        }
    }

    private fun getIntentData() {
        try {
            val args: MovieFragmentArgs by navArgs()
            movieData = args.movieData
        } catch (_: Exception) {
        }
        movieId =
            arguments?.getString(getString(R.string.intent_data_for_movies_collection_movie_id))
        collectionId =
            arguments?.getString(getString(R.string.intent_data_for_movies_collection_collection_id))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}