package com.ariabagas.mandiriflix.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ariabagas.mandiriflix.databinding.ItemMovieBinding
import com.ariabagas.mandiriflix.core.model.Movie

class MovieAdapter(
    private val isGrid: Boolean = false,
    private val isCredits: Boolean = false
) : ListAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback) {
    inner class HorizontalViewHolder private constructor(val view: ItemMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HorizontalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> {
                holder.view.apply {
                    isGrid = this@MovieAdapter.isGrid
                    isCredits = this@MovieAdapter.isCredits
                    movie = getItem(position)
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}