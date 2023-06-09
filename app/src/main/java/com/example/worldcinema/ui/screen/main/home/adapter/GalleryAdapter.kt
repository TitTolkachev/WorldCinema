package com.example.worldcinema.ui.screen.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldcinema.databinding.GalleryItemHorizontalBinding
import com.example.worldcinema.databinding.GalleryItemVerticalBinding
import com.example.worldcinema.ui.model.MoviePoster

/**
 * @param viewType Тип view item (1 - для маленьких вертикальных, 2 - для больших горизонтальных)
 * @param movieActionListener Реализация для IMovieActionListener
 */
class GalleryAdapter(
    private val viewType: Int,
    private val movieActionListener: IMovieActionListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var data: MutableList<MoviePoster> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class GalleryVerticalViewHolder(val binding: GalleryItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: MoviePoster) {
            with(binding.imageViewVerticalGalleryItem) {
                Glide.with(this).load(poster.poster).into(this)
                tag = poster.movieId
            }
        }
    }

    class GalleryHorizontalViewHolder(val binding: GalleryItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: MoviePoster) {
            with(binding.imageViewHorizontalGalleryItem) {
                Glide.with(this).load(poster.poster).into(this)
                tag = poster.movieId
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == 1) {
            val binding = GalleryItemVerticalBinding.inflate(inflater, parent, false)

            GalleryVerticalViewHolder(binding)
        } else {
            val binding = GalleryItemHorizontalBinding.inflate(inflater, parent, false)

            GalleryHorizontalViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(holder.itemViewType)) {
            1 -> {
                val viewHolder = holder as GalleryVerticalViewHolder
                viewHolder.bind(data[position])
                with(viewHolder.binding) {
                    imageViewVerticalGalleryItem.setOnClickListener(this@GalleryAdapter)
                }
            }
            2 -> {
                val viewHolder = holder as GalleryHorizontalViewHolder
                viewHolder.bind(data[position])
                with(viewHolder.binding) {
                    imageViewHorizontalGalleryItem.setOnClickListener(this@GalleryAdapter)
                }
            }
        }
    }

    override fun onClick(view: View) {
        val movieId: String = view.tag as String
        movieActionListener.onItemClicked(movieId)
    }
}

interface IMovieActionListener {
    fun onItemClicked(movieId: String)
}