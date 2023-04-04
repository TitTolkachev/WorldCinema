package com.example.worldcinema.ui.main.collections.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.databinding.CollectionsItemBinding
import com.example.worldcinema.ui.main.collections.model.UsersCollection

class CollectionsAdapter(private val collectionActionListener: ICollectionActionListener) :
    RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder>(), View.OnClickListener {

    var data: MutableList<UsersCollection> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CollectionsViewHolder(val binding: CollectionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collection: UsersCollection) {
            // TODO(Подставлять правильную картинку)

            with(binding) {
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
        holder.bind(data[position])
        holder.binding.root.setOnClickListener(this@CollectionsAdapter)
    }

    override fun onClick(view: View) {
        val collectionId: String = view.tag as String
        collectionActionListener.onItemClicked(collectionId)
    }
}

interface ICollectionActionListener {
    fun onItemClicked(collectionId: String)
}