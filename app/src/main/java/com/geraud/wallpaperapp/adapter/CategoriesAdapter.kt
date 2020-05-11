package com.geraud.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.model.Category
import kotlinx.android.synthetic.main.cat_item.view.*

class CategoriesAdapter(
    var categories: List<Category>,
    val context: Context,
    private var clickListener: OnItemClickedListener
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], context, clickListener)
    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.category_image
        private val name = view.category_name

        fun bind(category: Category, context: Context, action: OnItemClickedListener) {
            name.text = category.name
            Glide.with(context).asBitmap().load(category.image_url).into(imageView)

            name.setOnClickListener {
                action.onCategoryItemCLicked(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemVIew =
            LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CategoryViewHolder(itemVIew)
    }

    interface OnItemClickedListener {
        fun onCategoryItemCLicked(category: Category)
    }


}