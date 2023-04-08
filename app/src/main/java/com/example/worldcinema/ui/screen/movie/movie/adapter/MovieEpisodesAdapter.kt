package com.example.worldcinema.ui.screen.movie.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldcinema.databinding.MovieEpisodesItemBinding
import com.example.worldcinema.ui.model.MovieEpisode

class MovieEpisodesAdapter(private val episodeActionListener: IMovieEpisodeActionListener) :
    RecyclerView.Adapter<MovieEpisodesAdapter.MovieEpisodeViewHolder>(), View.OnClickListener {

    var data: MutableList<MovieEpisode> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MovieEpisodeViewHolder(val binding: MovieEpisodesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieEpisode: MovieEpisode) {
            with(binding) {
                Glide.with(imageViewEpisodeItemImage).load(movieEpisode.preview)
                    .into(imageViewEpisodeItemImage)
                root.tag = movieEpisode.episodeId
                textViewIpisodeItemTitle.text = movieEpisode.name
                textViewEpisodeItemText.text = movieEpisode.description
                textViewEpisodeItemYear.text = movieEpisode.year
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieEpisodeViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = MovieEpisodesItemBinding.inflate(inflater, parent, false)

        return MovieEpisodeViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieEpisodeViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.root.setOnClickListener(this@MovieEpisodesAdapter)
    }

    override fun onClick(view: View) {
        val episodeId: String = view.tag as String
        episodeActionListener.onItemClicked(episodeId)
    }
}

interface IMovieEpisodeActionListener {
    fun onItemClicked(episodeId: String)
}