package com.example.worldcinema.ui.movie.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.R
import com.example.worldcinema.databinding.MovieImagesItemBinding

class MovieImagesAdapter :
    RecyclerView.Adapter<MovieImagesAdapter.MovieViewHolder>() {

    var data: List<String> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MovieViewHolder(val binding: MovieImagesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieImage: String) {
            // TODO(Найти картинку в интернете)

            with(binding.imageViewMovieItem) {
                setImageResource(R.drawable.test_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = MovieImagesItemBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position])
    }
}