package com.example.worldcinema.ui.screen.collection.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.databinding.ActivityMoviesCollectionBinding
import com.example.worldcinema.ui.screen.collection.movies.adapter.IMoviesCollectionActionListener
import com.example.worldcinema.ui.screen.collection.movies.adapter.MoviesCollectionAdapter
import com.example.worldcinema.ui.screen.collection.update.UpdateCollectionActivity

class MoviesCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesCollectionBinding
    private lateinit var viewModel: MoviesCollectionViewModel

    private val args: MoviesCollectionActivityArgs by navArgs()

    private lateinit var moviesAdapter: MoviesCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MoviesCollectionViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        binding.imageButtonEdit.setOnClickListener {
            val intent =
                Intent(applicationContext, UpdateCollectionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("collection", args.collection)
            applicationContext.startActivity(intent)
        }

        initMoviesRecyclerView()

        setContentView(binding.root)
    }

    private fun initMoviesRecyclerView() {

        binding.CollectionRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        moviesAdapter = MoviesCollectionAdapter(object : IMoviesCollectionActionListener {
            override fun onItemClicked(movieId: String) {
                viewModel.onItemClicked(movieId)
            }
        })
        binding.CollectionRecyclerView.adapter = moviesAdapter

        viewModel.movies.observe(this) {
            if (it != null) {
                moviesAdapter.data = it
            }
        }
    }
}