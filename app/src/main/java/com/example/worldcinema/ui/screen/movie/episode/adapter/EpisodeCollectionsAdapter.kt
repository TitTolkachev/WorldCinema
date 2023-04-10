package com.example.worldcinema.ui.screen.movie.episode.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.databinding.EpisodeCollectionItemBinding
import com.example.worldcinema.ui.model.UsersCollection

class EpisodeCollectionsAdapter(private val collectionActionListener: IEpisodeCollectionActionListener) :
    RecyclerView.Adapter<EpisodeCollectionsAdapter.EpisodeCollectionViewHolder>(), View.OnClickListener {

    var data: MutableList<UsersCollection> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class EpisodeCollectionViewHolder(val binding: EpisodeCollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collection: UsersCollection) {
            with(binding) {
                root.tag = collection.collectionId
                textViewEpisodeCollectionName.text = collection.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeCollectionViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = EpisodeCollectionItemBinding.inflate(inflater, parent, false)

        return EpisodeCollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: EpisodeCollectionViewHolder, position: Int) {
        holder.bind(data[position])
        holder.binding.root.setOnClickListener(this@EpisodeCollectionsAdapter)
    }

    override fun onClick(view: View) {
        val collectionId = view.tag as String
        collectionActionListener.onItemClicked(collectionId)
    }
}

interface IEpisodeCollectionActionListener {
    fun onItemClicked(collectionId: String)
}