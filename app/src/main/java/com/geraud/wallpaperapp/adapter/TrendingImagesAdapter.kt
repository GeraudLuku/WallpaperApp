package com.geraud.wallpaperapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrendingImagesAdapter(private val context: Context,
                            private var clickListener: OnItemClickedListener) :
    RecyclerView.Adapter<TrendingImagesAdapter.TrendingImagesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingImagesViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TrendingImagesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class TrendingImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bind(context: Context, action: OnItemClickedListener) {

        }
    }


    interface OnItemClickedListener {
        fun onTrendingImageCLicked()
    }
}