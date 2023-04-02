package com.example.worldcinema.ui.movie.episode

import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.databinding.FragmentEpisodeBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var viewModel: EpisodeViewModel

    private lateinit var videoView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[EpisodeViewModel::class.java]

        navController = findNavController()

        binding.imageButtonEpisodeArrowBack.setOnClickListener {
            navController.popBackStack()
        }

        videoView = binding.videoView

        with(videoView.layoutParams) {
            height = Resources.getSystem().displayMetrics.widthPixels * 7 / 12
        }

        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        videoView.player = exoPlayer
        videoView.keepScreenOn = true
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {

            }
        })
        val videoSource =
            Uri.parse("https://drive.google.com/uc?export=view&id=1ra6f2jGr6Jja8UXptmpGDHO9ERiMmE6d")
        val mediaItem = MediaItem.fromUri(videoSource)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        videoView.setOnClickListener {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            }
        }

        return binding.root
    }

    override fun onStop() {
        exoPlayer.stop()
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