package com.example.worldcinema.ui.screen.movie.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityMovieBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.model.MovieEpisode
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.discussions.chat.ChatActivity
import com.example.worldcinema.ui.screen.movie.episode.EpisodeActivity
import com.example.worldcinema.ui.screen.movie.movie.adapter.IMovieEpisodeActionListener
import com.example.worldcinema.ui.screen.movie.movie.adapter.MovieEpisodesAdapter
import com.example.worldcinema.ui.screen.movie.movie.adapter.MovieImagesAdapter

class MovieActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener {

    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel

    private lateinit var movieImagesAdapter: MovieImagesAdapter
    private lateinit var movieEpisodesAdapter: MovieEpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentMovie = try {
            val args: MovieActivityArgs by navArgs()
            args.movieData
        } catch (_: Exception) {
            null
        }
        val intentMovieId =
            intent.getStringExtra(getString(R.string.intent_data_for_movies_collection_movie_id))
        val intentCollectionId =
            intent.getStringExtra(getString(R.string.intent_data_for_movies_collection_collection_id))

        _binding = ActivityMovieBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(
                this,
                intentMovieId,
                intentCollectionId,
                intentMovie
            )
        )[MovieViewModel::class.java]

        binding.imageButtonMovieArrowBack.setOnClickListener {
            finish()
        }

        viewModel.isMovieDataLoading.observe(this) {
            if (it) {
                binding.movieContent.visibility = View.GONE
                binding.progressBarMovie.visibility = View.VISIBLE
            } else {
                binding.movieContent.visibility = View.VISIBLE
                binding.progressBarMovie.visibility = View.GONE
                onMovieDataLoaded(layoutInflater)
            }
        }

        setContentView(binding.root)
    }

    private fun onMovieDataLoaded(inflater: LayoutInflater) {

        binding.imageButtonMovieChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(
                getString(R.string.intent_data_for_chat_chat_id),
                viewModel.movie.value?.chatInfo?.chatId ?: ""
            )
            startActivity(intent)
        }

        binding.buttonWatchMovie.setOnClickListener {
            if (!viewModel.movieEpisodes.value.isNullOrEmpty())
                showEpisode(viewModel.movieEpisodes.value!![0])
        }

        viewModel.isEpisodesDataLoading.observe(this) {
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

        viewModel.movieAge.observe(this) {
            if (it != null) {
                with(binding.textViewMovieAge) {
                    text = it
                    setTextColor(
                        ContextCompat.getColor(
                            this@MovieActivity,
                            viewModel.getMovieAgeColor()
                        )
                    )
                }
            }
        }

        viewModel.movie.observe(this) {
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

        viewModel.movieImages.observe(this) {
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
                if (episode != null) {
                    showEpisode(episode)
                }
            }
        })
        binding.RecyclerViewMovieEpisodes.adapter = movieEpisodesAdapter

        viewModel.movieEpisodes.observe(this) {
            if (it != null)
                movieEpisodesAdapter.data = it
        }
    }

    private fun showEpisode(episode: MovieEpisode) {
        if (viewModel.movie.value != null) {
            val intent = Intent(this, EpisodeActivity::class.java)
            intent.putExtra(
                getString(R.string.intent_data_for_episode_movie),
                viewModel.movie.value!!
            )
            intent.putExtra(
                getString(R.string.intent_data_for_episode_episode),
                episode
            )
            intent.putExtra(
                getString(R.string.intent_data_for_episode_episodes_count),
                viewModel.getEpisodesCount()
            )
            intent.putExtra(
                getString(R.string.intent_data_for_episode_movie_years),
                viewModel.getMovieYears()
            )
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun alertDialogExit() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }
}