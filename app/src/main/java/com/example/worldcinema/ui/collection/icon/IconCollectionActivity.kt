package com.example.worldcinema.ui.collection.icon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivityIconCollectionBinding

class IconCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIconCollectionBinding
    private lateinit var viewModel: IconCollectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIconCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[IconCollectionViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }
}