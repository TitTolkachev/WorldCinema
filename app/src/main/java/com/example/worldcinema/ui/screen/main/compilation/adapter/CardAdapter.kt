package com.example.worldcinema.ui.screen.main.compilation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldcinema.databinding.CompilationCardBinding
import com.example.worldcinema.ui.model.Card

class CardAdapter :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var data: MutableList<Card> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CardViewHolder(val binding: CompilationCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {

            with(binding.cardImage) {
                Glide.with(this).load(card.image).into(this)
                clipToOutline = true
                tag = card.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = CompilationCardBinding.inflate(inflater, parent, false)

        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.bind(data[position])
    }
}