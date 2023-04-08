package com.example.worldcinema.ui.screen.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.worldcinema.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}