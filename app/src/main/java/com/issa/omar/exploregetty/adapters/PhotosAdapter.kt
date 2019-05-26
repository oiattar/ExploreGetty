package com.issa.omar.exploregetty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.issa.omar.exploregetty.R
import com.squareup.picasso.Picasso

class PhotosAdapter(private val context: Context, private val photoUrls: List<String>):
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val image = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false) as ImageView
        return PhotosViewHolder(image)
    }

    override fun getItemCount(): Int = photoUrls.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        Picasso.get().load(photoUrls[position]).into(holder.image)
    }

    class PhotosViewHolder(val image: ImageView): RecyclerView.ViewHolder(image)
}