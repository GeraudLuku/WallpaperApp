package com.geraud.wallpaperapp.features

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
    private lateinit var navController: NavController

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
        navController = findNavController()

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

            //add photos to recyclerview
            trendingPhotoAdapter.photos.addAll(trendingPhotos.photos)
            trendingPhotoAdapter.notifyDataSetChanged()

            if (swipyrefreshlayout.isRefreshing)
                swipyrefreshlayout.isRefreshing = false

        })

        //if edit text clicked open search fragment and pass text
        editText.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event?.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (editText.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                        // your action here
                        Log.d(TAG, "drawable right clicked")

                        //edit text
                        val query = editText.text.toString().trim()

                        //get text in search bar and determine if it is empty
                        if (query.length > 0) {
                            //navigate to search fragment
                            val action =
                                HomeFragmentDirections.actionHomeFragment2ToSearchFragment(query)
                            navController.navigate(action)
                        }

                        return true
                    }
                }

                return false
            }

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
