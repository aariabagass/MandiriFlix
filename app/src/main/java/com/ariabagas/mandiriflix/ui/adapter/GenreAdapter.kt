package com.ariabagas.mandiriflix.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ariabagas.mandiriflix.R
import com.ariabagas.mandiriflix.databinding.ItemGenreBinding
import com.ariabagas.mandiriflix.core.model.Genre
import com.ariabagas.mandiriflix.util.Type

class GenreAdapter(
    private val typeType: Type
) : ListAdapter<Pair<Int, String>, GenreAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(val view: ItemGenreBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_genre, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            detailType = this@GenreAdapter.typeType
            genre = Genre(id = getItem(position).first, name = getItem(position).second)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Pair<Int, String>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Int, String>, newItem: Pair<Int, String>
        ): Boolean = oldItem.first == newItem.first

        override fun areContentsTheSame(
            oldItem: Pair<Int, String>, newItem: Pair<Int, String>
        ): Boolean = oldItem == newItem
    }
}