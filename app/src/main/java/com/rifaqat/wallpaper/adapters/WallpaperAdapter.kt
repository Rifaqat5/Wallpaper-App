package com.rifaqat.wallpaper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifaqat.wallpaper.R
import com.rifaqat.wallpaper.databinding.HomeSampleBinding
import com.rifaqat.wallpaper.listeners.RecyclerViewItemClick
import com.rifaqat.wallpaper.model.Images
import com.squareup.picasso.Picasso

class WallpaperAdapter(
    private val context: Context,
    private val list: List<Images>,
    private val listener: RecyclerViewItemClick
) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        return WallpaperViewHolder(
            LayoutInflater.from(context).inflate(R.layout.home_sample, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val photo = list[position]
        Picasso.get().load(photo.src.medium)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageViewList)

        // Set the click listener
        holder.homeListContainer.setOnClickListener {
            listener.rvItemClick(photo)
        }
    }

    inner class WallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = HomeSampleBinding.bind(itemView)
        val homeListContainer = binding.homeListContainer
        val imageViewList = binding.imageViewList
    }
}
