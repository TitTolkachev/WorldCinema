package com.example.worldcinema.ui.collection.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivityCreateCollectionBinding

class CreateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCollectionBinding
    private lateinit var viewModel: CreateCollectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CreateCollectionViewModel::class.java]

        setContentView(binding.root)
    }
}