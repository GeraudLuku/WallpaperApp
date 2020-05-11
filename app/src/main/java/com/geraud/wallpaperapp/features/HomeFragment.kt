package com.geraud.wallpaperapp.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.adapter.CategoriesAdapter
import com.geraud.wallpaperapp.adapter.TrendingImagesAdapter
import com.geraud.wallpaperapp.model.Category
import com.geraud.wallpaperapp.viewmodel.PexelsViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), CategoriesAdapter.OnItemClickedListener,
    TrendingImagesAdapter.OnItemClickedListener {

    private var TAG = "HOME_FRAGMENT"
    private var NUMBER_OF_COLUMBS = 2
    private lateinit var pexelsViewModel: PexelsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set toolbar
        if (activity is AppCompatActivity)
            (activity as AppCompatActivity).setSupportActionBar(app_bar)


        //init view model
        pexelsViewModel = ViewModelProvider(this).get(PexelsViewModel::class.java)


        //categories recycler view instantiation
        category_recyclerview.adapter =
            CategoriesAdapter(pexelsViewModel.categories, view.context, this)
        category_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        category_recyclerview.setHasFixedSize(true)

        //trending images recyclerview instantiation
        trending_pictures_view.adapter = TrendingImagesAdapter(view.context, this)
        trending_pictures_view.layoutManager = GridLayoutManager(view.context, NUMBER_OF_COLUMBS)
        trending_pictures_view.setHasFixedSize(true)
    }

    override fun onCategoryItemCLicked(category: Category) {
        TODO("Not yet implemented")
    }

    override fun onTrendingImageCLicked() {
        TODO("Not yet implemented")
    }

}
