package com.example.worldcinema.ui.screen.movie.episode

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
import com.bumptech.glide.Glide
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentEpisodeBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EpisodeViewModel
    private lateinit var navController: NavController

    private val args: EpisodeFragmentArgs by navArgs()

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
        exoPlayer.seekTo(120 * 1000) // TODO(Подствалять правильное время)
        exoPlayer.prepare()

        videoView.setOnClickListener {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            }
        }
    }

    override fun onStop() {
        exoPlayer.stop()
        viewModel.saveVideoPosition(exoPlayer.contentPosition, exoPlayer.contentDuration)
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