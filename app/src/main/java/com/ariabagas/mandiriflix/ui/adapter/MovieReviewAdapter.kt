package com.ariabagas.mandiriflix.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ariabagas.mandiriflix.R
import com.ariabagas.mandiriflix.databinding.ItemMovieReviewBinding
import com.ariabagas.mandiriflix.core.model.MovieReview

class MovieReviewAdapter : ListAdapter<MovieReview, MovieReviewAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(val view: ItemMovieReviewBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_movie_review, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            movieReview = getItem(position)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<MovieReview>() {
        override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean =
            oldItem == newItem
    }
}