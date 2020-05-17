package com.geraud.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.model.PhotoX
import kotlinx.android.synthetic.main.photo_item.view.*


class SearchedImagesAdapter(
    var photos: ArrayList<PhotoX>,
    private val context: Context,
    private var clickListener: OnItemClickedListener
) :
    RecyclerView.Adapter<SearchedImagesAdapter.SearchedImagesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedImagesViewHolder {
        val itemVIew =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return SearchedImagesViewHolder(itemVIew)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: SearchedImagesViewHolder, position: Int) {
        holder.bind(photos[position], context, clickListener)
    }

    class SearchedImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.photo_prev

        fun bind(photo: PhotoX, context: Context, action: OnItemClickedListener) {

            Glide.with(context).asBitmap().load(photo.src.portrait).into(imageView)

            imageView.setOnClickListener {
                action.onSearchedImageCLicked(photo)
            }

        }
    }

    fun clear() {
        val size = photos.size

        if (size > 0) {
            photos.clear()
            notifyItemRangeRemoved(0, size)
        }
    }


    interface OnItemClickedListener {
        fun onSearchedImageCLicked(photo: PhotoX)
    }
}