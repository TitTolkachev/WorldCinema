package com.example.worldcinema.ui.screen.collection.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.ActivityMoviesCollectionBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.screen.auth.sign_in.SignInActivity
import com.example.worldcinema.ui.screen.collection.movies.adapter.IMoviesCollectionActionListener
import com.example.worldcinema.ui.screen.collection.movies.adapter.MoviesCollectionAdapter
import com.example.worldcinema.ui.screen.collection.movies.helper.SimpleItemTouchHelperCollectionCallback
import com.example.worldcinema.ui.screen.collection.movies.helper.SwipeListener
import com.example.worldcinema.ui.screen.collection.update.UpdateCollectionActivity
import com.example.worldcinema.ui.screen.movie.movie.MovieActivity

class MoviesCollectionActivity : AppCompatActivity(), AlertDialog.IAlertDialogExitListener,
    AlertDialog.IAlertDialogListener {

    private lateinit var binding: ActivityMoviesCollectionBinding
    private lateinit var viewModel: MoviesCollectionViewModel

    private val args: MoviesCollectionActivityArgs by navArgs()

    private lateinit var moviesAdapter: MoviesCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesCollectionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this,
            MoviesCollectionViewModelFactory(this, args.collection)
        )[MoviesCollectionViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }

        binding.textViewCollectionTitle.text = args.collection.name

        binding.imageButtonEdit.setOnClickListener {
            val intent =
                Intent(applicationContext, UpdateCollectionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("collection", args.collection)
            applicationContext.startActivity(intent)
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressBarMoviesCollection.visibility = View.VISIBLE
                binding.CollectionRecyclerView.visibility = View.GONE
            } else {
                binding.progressBarMoviesCollection.visibility = View.GONE
                binding.CollectionRecyclerView.visibility = View.VISIBLE
                onDataLoaded()
            }
        }

        viewModel.isCollectionFavourite.observe(this) {
            if (it) {
                binding.imageButtonEdit.visibility = View.GONE
            } else {
                binding.imageButtonEdit.visibility = View.VISIBLE
            }
        }

        viewModel.showAlertDialog.observe(this) {
            if (it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
            }
        }

        setContentView(binding.root)
    }

    private fun onDataLoaded() {
        initMoviesRecyclerView()
    }

    private fun initMoviesRecyclerView() {
        binding.CollectionRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        moviesAdapter = MoviesCollectionAdapter(object : IMoviesCollectionActionListener {
            override fun onItemClicked(movieId: String) {
                showMovie(movieId)
            }
        }, object : SwipeListener {
            override fun deleteMovie(position: Int) {
                viewModel.deleteMovie(position)
            }
        })

        val callback = SimpleItemTouchHelperCollectionCallback(moviesAdapter)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.CollectionRecyclerView)

        binding.CollectionRecyclerView.adapter = moviesAdapter

        viewModel.movies.observe(this) {
            if (it != null) {
                moviesAdapter.data = it
            }
        }
    }

    private fun showMovie(movieId: String) {
        val intent = Intent(applicationContext, MovieActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(getString(R.string.intent_data_for_movies_collection_movie_id), movieId)
        intent.putExtra(
            getString(R.string.intent_data_for_movies_collection_collection_id),
            viewModel.getCollectionId()
        )
        applicationContext.startActivity(intent)
    }

    override fun alertDialogExit() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    override fun alertDialogRetry() {
        viewModel.reload()
    }

    override fun onAlertDialogDismiss() {}
}