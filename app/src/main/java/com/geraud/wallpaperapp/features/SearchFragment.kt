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
import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.adapter.SearchedImagesAdapter
import com.geraud.wallpaperapp.model.PhotoX
import com.geraud.wallpaperapp.viewmodel.PexelsViewModel
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import kotlinx.android.synthetic.main.fragment_search.*

private const val TAG = "SEARCH_FRAGMENT"
private const val NUMBER_OF_COLUMNS = 2

class SearchFragment : Fragment(), SearchedImagesAdapter.OnItemClickedListener,
    SwipyRefreshLayout.OnRefreshListener {

    private lateinit var pexelsViewModel: PexelsViewModel
    private lateinit var navController: NavController

    private lateinit var searchedImagesAdapter: SearchedImagesAdapter

    private var queryString: String? = null

    private var page = 1

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
        queryString = arguments?.let { SearchFragmentArgs.fromBundle(it).queryString }
        search_query.hint = queryString

        searchIcon1.setOnClickListener {
            //hide keyboard
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

            //edit text
            val query = search_query.text.toString().trim()

            //get text in search bar and determine if it is empty
            if (query.length > 0) {
                //search query
                queryString = query
                page = 1
                pexelsViewModel.setSearchQuery(page, queryString.toString())

                //clear recyclerview adapter
                searchedImagesAdapter.clear()
            }
        }

        //search recycler adapter
        searchedImagesAdapter = SearchedImagesAdapter(arrayListOf(), view.context, this)
        search_view.adapter = searchedImagesAdapter
        search_view.layoutManager = GridLayoutManager(view.context, NUMBER_OF_COLUMNS)

        //query data
        pexelsViewModel.setSearchQuery(page, queryString.toString())
        pexelsViewModel.searchedPhotos.observe(viewLifecycleOwner, Observer { searchedPhotos ->
            Log.d(TAG, "Sucessfully observed searched photos $searchedPhotos")

            //add photos to recyclerview
            searchedImagesAdapter.photos.addAll(searchedPhotos.photos)
            searchedImagesAdapter.notifyDataSetChanged()

            if (refreshlayout.isRefreshing)
                refreshlayout.isRefreshing = false

        })

    }

    override fun onSearchedImageCLicked(photo: PhotoX) {
        Log.d(TAG, "Image clicked $photo")

        val action = SearchFragmentDirections.actionSearchFragmentToImageFragment(photo.src)
        navController.navigate(action)
    }

    override fun onRefresh(direction: SwipyRefreshLayoutDirection?) {
        Log.d(TAG, "onRefresh")

        //query photos
        page = page.inc()
        pexelsViewModel.setSearchQuery(page, queryString.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack()
        }

        return super.onOptionsItemSelected(item);
    }

}
