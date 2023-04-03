package com.example.worldcinema.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.worldcinema.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}