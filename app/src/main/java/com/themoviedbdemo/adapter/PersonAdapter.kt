package com.themoviedbdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.themoviedbdemo.databinding.ItemPersonBinding
import com.themoviedbdemo.models.responcemodel.Person

class PersonAdapter(private val onItemClick: (Person) -> Unit) : PagingDataAdapter<Person, PersonAdapter.PersonViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        person?.let { holder.bind(it) }
    }

    inner class PersonViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.name.text = person.name
            binding.popularity.text = "Popularity: ${person.popularity}"

            Glide.with(binding.image.context)
                .load("https://image.tmdb.org/t/p/w500${person.profile_path}")
                .into(binding.image)
            val knownForText = person.known_for?.joinToString(", ") { it.title ?: it.name ?: "" }
            binding.knownFor.text = knownForText ?: "No known works available"

            binding.root.setOnClickListener {
                onItemClick(person)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }
}
