package com.example.worldcinema.ui.collection.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.ActivityUpdateCollectionBinding

class UpdateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCollectionBinding
    private lateinit var viewModel: UpdateCollectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[UpdateCollectionViewModel::class.java]

        setContentView(binding.root)
    }
}