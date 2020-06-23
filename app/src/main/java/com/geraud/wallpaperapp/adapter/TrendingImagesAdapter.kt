package com.geraud.wallpaperapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.model.Photo
import kotlinx.android.synthetic.main.photo_item.view.*

class TrendingImagesAdapter(
    var photos: ArrayList<Photo>,
    private val context: Context,
    private var clickListener: OnItemClickedListener
) :
    RecyclerView.Adapter<TrendingImagesAdapter.TrendingImagesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingImagesViewHolder {
        val itemVIew =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return TrendingImagesViewHolder(itemVIew)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: TrendingImagesViewHolder, position: Int) {
        holder.bind(photos[position], context, clickListener)
    }

    class TrendingImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.photo_prev
        private val progressBar = view.progressBar

        fun bind(photo: Photo, context: Context, action: OnItemClickedListener) {

            //loading image on view
            Glide.with(context).asBitmap().load(photo.src.portrait)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {//when it is ready hide progress bar
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(imageView)

            //seton click listener on image
            imageView.setOnClickListener {
                action.onTrendingImageCLicked(photo)
            }

        }
    }


    interface OnItemClickedListener {
        fun onTrendingImageCLicked(photo: Photo)
    }
}