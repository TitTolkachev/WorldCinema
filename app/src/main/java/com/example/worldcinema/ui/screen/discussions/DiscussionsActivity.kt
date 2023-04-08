package com.example.worldcinema.ui.screen.discussions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.worldcinema.databinding.ActivityDiscussionsBinding

class DiscussionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscussionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiscussionsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}