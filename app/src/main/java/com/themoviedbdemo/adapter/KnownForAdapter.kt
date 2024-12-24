package com.themoviedbdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.themoviedbdemo.databinding.ItemKnownForBinding
import com.themoviedbdemo.models.responcemodel.KnownFor

class KnownForAdapter(private val knownForList: List<KnownFor>) :
    RecyclerView.Adapter<KnownForAdapter.KnownForViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnownForViewHolder {
        val binding = ItemKnownForBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KnownForViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KnownForViewHolder, position: Int) {
        val knownFor = knownForList[position]
        holder.bind(knownFor)
    }

    override fun getItemCount(): Int = knownForList.size

    inner class KnownForViewHolder(private val binding: ItemKnownForBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(knownFor: KnownFor) {
            Glide.with(binding.image.context)
                .load("https://image.tmdb.org/t/p/w500${knownFor.poster_path}")
                .into(binding.image)
            binding.title.text = knownFor.title ?: knownFor.name
        }
    }
}
