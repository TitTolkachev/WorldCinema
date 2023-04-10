package com.example.worldcinema.ui.screen.collection.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldcinema.databinding.CollectionMovieItemBinding
import com.example.worldcinema.ui.model.MovieInCollection

class MoviesCollectionAdapter(private val moviesCollectionActionListener: IMoviesCollectionActionListener) :
    RecyclerView.Adapter<MoviesCollectionAdapter.MoviesCollectionViewHolder>(),
    View.OnClickListener {

    var data: MutableList<MovieInCollection> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MoviesCollectionViewHolder(val binding: CollectionMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieInCollection) {
            with(binding) {
                Glide.with(imageViewCollectionMovieItem).load(movie.poster)
                    .into(imageViewCollectionMovieItem)
                textViewCollectionMovieItemTitle.text = movie.name
                textViewCollectionMovieItemText.text = movie.description
                root.tag = movie.movieId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesCollectionViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = CollectionMovieItemBinding.inflate(inflater, parent, false)

        return MoviesCollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MoviesCollectionViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.root.setOnClickListener(this@MoviesCollectionAdapter)
    }

    override fun onClick(view: View) {
        val movieId: String = view.tag as String
        moviesCollectionActionListener.onItemClicked(movieId)
    }
}

interface IMoviesCollectionActionListener {
    fun onItemClicked(movieId: String)
}