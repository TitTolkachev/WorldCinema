package com.example.worldcinema.ui.screen.movie.episode

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentEpisodeBinding
import com.example.worldcinema.ui.screen.discussions.chat.ChatActivity
import com.example.worldcinema.ui.screen.movie.episode.adapter.EpisodeCollectionsAdapter
import com.example.worldcinema.ui.screen.movie.episode.adapter.IEpisodeCollectionActionListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EpisodeViewModel
    private lateinit var navController: NavController

    private val args: EpisodeFragmentArgs by navArgs()

    private lateinit var episodeCollectionsAdapter: EpisodeCollectionsAdapter

    private lateinit var videoView: StyledPlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            EpisodeViewModelFactory(
                requireContext(),
                args.movie,
                args.episode,
                args.episodesCount,
                args.movieYears
            )
        )[EpisodeViewModel::class.java]
        navController = findNavController()

        binding.imageButtonEpisodeArrowBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.imageViewEpisodeMessenger.setOnClickListener {
            val intent = Intent(view?.context, ChatActivity::class.java)
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

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
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

        viewModel.episode.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textViewEpisodeTitle.text = it.name

                initVideoPlayer()
            }
        }

        viewModel.episodeTime.observe(viewLifecycleOwner) {
            exoPlayer.seekTo(it.toLong() * 1000)
        }

        initCollectionsRecyclerView()

        return binding.root
    }

    private fun initVideoPlayer() {

        videoView = binding.videoView

        with(videoView.layoutParams) {
            height = Resources.getSystem().displayMetrics.widthPixels * 7 / 12
        }

        exoPlayer = ExoPlayer.Builder(requireContext()).build()
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

        viewModel.episodeCollections.observe(viewLifecycleOwner) {
            if (it != null) {
                episodeCollectionsAdapter.data = it
            }
        }
    }

    private fun addMovieToCollection(collectionId: String) {
        viewModel.addMovieToCollection(collectionId)
        binding.EpisodeCollectionsRecyclerView.visibility = View.GONE
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

    override fun onDestroyView() {
        exoPlayer.stop()
        _binding = null
        super.onDestroyView()
    }
}