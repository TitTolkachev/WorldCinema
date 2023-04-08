package com.example.worldcinema.ui.screen.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Navigation.findNavController(this, R.id.nav_host_fragment_activity_movie)
            .setGraph(R.navigation.movie_navigation, intent.extras)
    }
}