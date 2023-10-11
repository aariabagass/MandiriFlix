package com.ariabagas.mandiriflix.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ariabagas.mandiriflix.R
import com.ariabagas.mandiriflix.databinding.ItemImageBinding
import com.ariabagas.mandiriflix.core.model.Image
import com.ariabagas.mandiriflix.util.Constants

class ImageAdapter(
    private val isPortrait: Boolean = false,
    private val isGrid: Boolean = false
) : ListAdapter<Image, ImageAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(val view: ItemImageBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                val bundle = bundleOf(
                    Constants.IMAGE_LIST to currentList.toTypedArray(),
                    Constants.IMAGE_POSITION to adapterPosition
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            isGrid = this@ImageAdapter.isGrid
            isPortrait = this@ImageAdapter.isPortrait
            image = getItem(position)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.filePath == newItem.filePath

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem == newItem
    }
}