package com.geraud.wallpaperapp.features

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.adapter.CategoriesAdapter
import com.geraud.wallpaperapp.adapter.TrendingImagesAdapter
import com.geraud.wallpaperapp.model.Category
import com.geraud.wallpaperapp.model.Photo
import com.geraud.wallpaperapp.viewmodel.PexelsViewModel
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import kotlinx.android.synthetic.main.fragment_home.*

private const val TAG = "HOME_FRAGMENT"
private const val NUMBER_OF_COLUMNS = 2

class HomeFragment : Fragment(), CategoriesAdapter.OnItemClickedListener,
    TrendingImagesAdapter.OnItemClickedListener, SwipyRefreshLayout.OnRefreshListener {



    private var page = 1

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

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        //set toolbar
        if (activity is AppCompatActivity)
            (activity as AppCompatActivity).setSupportActionBar(app_bar)


        //init view model
        pexelsViewModel = ViewModelProvider(this).get(PexelsViewModel::class.java)

        swipyrefreshlayout.setOnRefreshListener(this)

        //categories recycler view instantiation
        category_recyclerview.adapter =
            CategoriesAdapter(pexelsViewModel.categories, view.context, this)
        category_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        category_recyclerview.setHasFixedSize(true)

        //trending images recyclerview instantiation
        val trendingPhotoAdapter = TrendingImagesAdapter(arrayListOf(), view.context, this)
        trending_pictures_view.adapter = trendingPhotoAdapter
        trending_pictures_view.layoutManager = GridLayoutManager(view.context, NUMBER_OF_COLUMNS)


        //observe curated/trending photos
        pexelsViewModel.setPageNumber(page)
        pexelsViewModel.trendingPhotos.observe(viewLifecycleOwner, Observer { trendingPhotos ->
            Log.d(TAG, "trending photos been observed... { $trendingPhotos }")

            if (swipyrefreshlayout.isRefreshing)
                swipyrefreshlayout.isRefreshing = false

            //add photos to recyclerview
            trendingPhotoAdapter.photos.addAll(trendingPhotos.photos)
            trendingPhotoAdapter.notifyDataSetChanged()

        })
    }

    override fun onCategoryItemCLicked(category: Category) {
        Log.d(TAG, "category clicked - $category")
    }

    override fun onTrendingImageCLicked(photo: Photo) {
        Log.d(TAG, "photo clicked { $photo }")
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {

        Log.d(TAG, "bottom of list reached")

        //query photos
        page = page.inc()
        pexelsViewModel.setPageNumber(page)
    }

}
