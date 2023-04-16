package com.example.worldcinema.ui.screen.main.collections.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.databinding.CollectionsItemBinding
import com.example.worldcinema.ui.model.UsersCollection

class CollectionsAdapter(
    private val collectionActionListener: ICollectionActionListener,
    private val icons: List<Int>
) : RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder>(), View.OnClickListener {

    var data: MutableList<UsersCollection> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CollectionsViewHolder(val binding: CollectionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collection: UsersCollection, icons: List<Int>) {
            with(binding) {
                imageView3.setImageResource(icons[collection.imageIndex])
                textView4.text = collection.name
                root.tag = collection.collectionId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = CollectionsItemBinding.inflate(inflater, parent, false)

        return CollectionsViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(data[position], icons)
        holder.binding.root.setOnClickListener(this@CollectionsAdapter)
    }

    override fun onClick(view: View) {
        val collectionId: String = view.tag as String
        for (c in data) {
            if (c.collectionId == collectionId) {
                collectionActionListener.onItemClicked(c)
                break
            }
        }
    }
}

interface ICollectionActionListener {
    fun onItemClicked(collection: UsersCollection)
}