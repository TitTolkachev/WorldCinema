package com.example.worldcinema.ui.screen.movie.episode

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityEpisodeBinding
import com.example.worldcinema.ui.model.Movie
import com.example.worldcinema.ui.model.MovieEpisode
import com.example.worldcinema.ui.screen.discussions.chat.ChatActivity
import com.example.worldcinema.ui.screen.movie.episode.adapter.EpisodeCollectionsAdapter
import com.example.worldcinema.ui.screen.movie.episode.adapter.IEpisodeCollectionActionListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class EpisodeActivity : AppCompatActivity() {

    private var _binding: ActivityEpisodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EpisodeViewModel

    private lateinit var episodeCollectionsAdapter: EpisodeCollectionsAdapter

    private lateinit var videoView: StyledPlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentMovie = getSerializable(getString(R.string.intent_data_for_episode_movie), Movie::class.java)
        val intentEpisode =getSerializable(getString(R.string.intent_data_for_episode_episode), MovieEpisode::class.java)
        val intentEpisodesCount = intent.getIntExtra(getString(R.string.intent_data_for_episode_episodes_count), 1)
        val intentMovieYears = intent.getStringExtra(getString(R.string.intent_data_for_episode_movie_years))!!

        _binding = ActivityEpisodeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            EpisodeViewModelFactory(
                this,
                intentMovie,
                intentEpisode,
                intentEpisodesCount,
                intentMovieYears
            )
        )[EpisodeViewModel::class.java]

        binding.imageButtonEpisodeArrowBack.setOnClickListener {
            finish()
        }

        binding.imageViewEpisodeMessenger.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(
                getString(R.string.intent_data_for_chat_chat_id),
                viewModel.movie.value?.chatInfo?.chatId ?: ""
            )
            startActivity(intent)
        }

        binding.imageViewEpisodeAddToCollection.setOnClickListener {
            if (binding.EpisodeCollectionsRecyclerView.visibility == View.GONE)
                binding.EpisodeCollectionsRecyclerView.visibility = View.VISIBLE
            else
                binding.EpisodeCollectionsRecyclerView.visibility = View.GONE
        }

        viewModel.movie.observe(this) { movie ->
            if (movie != null) {
                binding.textViewEpisodeDescriptionText.text = movie.description
                Glide.with(binding.imageViewEpisodePoster).load(movie.poster)
                    .into(binding.imageViewEpisodePoster)
                binding.textViewEpisodeMovieName.text = movie.name
                "${getString(R.string.movie_episodes_count_text)} ${viewModel.episodesCount}".also {
                    binding.textViewEpisodesSeasonsCount.text = it
                }
                binding.textViewEpisodeMovieYears.text = viewModel.movieYears
            }
        }

        viewModel.episode.observe(this) {
            if (it != null) {
                binding.textViewEpisodeTitle.text = it.name

                initVideoPlayer()
            }
        }

        viewModel.episodeTime.observe(this) {
            exoPlayer.seekTo(it.toLong() * 1000)
        }

        initCollectionsRecyclerView()

        setContentView(binding.root)
    }

    private fun initVideoPlayer() {

        videoView = binding.videoView

        with(videoView.layoutParams) {
            height = Resources.getSystem().displayMetrics.widthPixels * 7 / 12
        }

        exoPlayer = ExoPlayer.Builder(this).build()
        videoView.player = exoPlayer
        videoView.keepScreenOn = true

        val videoSource =
            Uri.parse(viewModel.episode.value?.filePath)
        val mediaItem = MediaItem.fromUri(videoSource)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        videoView.setOnClickListener {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            }
        }
    }

    private fun initCollectionsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(binding.root.context)
        binding.EpisodeCollectionsRecyclerView.layoutManager = linearLayoutManager

        episodeCollectionsAdapter =
            EpisodeCollectionsAdapter(object : IEpisodeCollectionActionListener {
                override fun onItemClicked(collectionId: String) {
                    addMovieToCollection(collectionId)
                }
            })
        binding.EpisodeCollectionsRecyclerView.adapter = episodeCollectionsAdapter

        viewModel.episodeCollections.observe(this) {
            if (it != null) {
                episodeCollectionsAdapter.data = it
            }
        }
    }

    private fun addMovieToCollection(collectionId: String) {
        viewModel.addMovieToCollection(collectionId)
        binding.EpisodeCollectionsRecyclerView.visibility = View.GONE
    }

    private fun <T : java.io.Serializable?> getSerializable(name: String, clazz: Class<T>): T
    {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(name, clazz)!!
        else
            intent.getSerializableExtra(name) as T
    }

    override fun onStop() {
        exoPlayer.stop()
        viewModel.saveVideoPosition(exoPlayer.contentPosition)
        super.onStop()
    }

    override fun onPause() {
        exoPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        exoPlayer.stop()
        _binding = null
        super.onDestroy()
    }
}