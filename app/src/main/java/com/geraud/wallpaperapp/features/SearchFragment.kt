package com.geraud.wallpaperapp.features

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.adapter.TrendingImagesAdapter
import com.geraud.wallpaperapp.model.Photo
import com.geraud.wallpaperapp.viewmodel.PexelsViewModel
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import kotlinx.android.synthetic.main.fragment_search.*

private const val TAG = "SEARCH_FRAGMENT"
private const val NUMBER_OF_COLUMNS = 2

class SearchFragment : Fragment(), TrendingImagesAdapter.OnItemClickedListener,
    SwipyRefreshLayout.OnRefreshListener {

    private lateinit var pexelsViewModel: PexelsViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        //init view model
        pexelsViewModel = ViewModelProvider(this).get(PexelsViewModel::class.java)
        navController = findNavController()

        //set toolbar
        if (activity is AppCompatActivity) {
            setHasOptionsMenu(true)
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        refreshlayout.setOnRefreshListener(this)

        //get argument text
        val query = arguments?.let { SearchFragmentArgs.fromBundle(it).queryString }
        search_query.hint = query

        //search recycler adapter
        val trendingPhotoAdapter = TrendingImagesAdapter(arrayListOf(), view.context, this)
        search_view.adapter = trendingPhotoAdapter
        search_view.layoutManager = GridLayoutManager(view.context, NUMBER_OF_COLUMNS)

        //query data

    }

    override fun onTrendingImageCLicked(photo: Photo) {
        Log.d(TAG, "Image clicked $photo")
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {
        Log.d(TAG, "onRefresh")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack()
        }

        return super.onOptionsItemSelected(item);
    }

}
