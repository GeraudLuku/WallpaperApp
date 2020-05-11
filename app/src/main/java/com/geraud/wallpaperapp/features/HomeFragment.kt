package com.geraud.wallpaperapp.features

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.geraud.wallpaperapp.R
import com.geraud.wallpaperapp.adapter.CategoriesAdapter
import com.geraud.wallpaperapp.model.Category
import com.geraud.wallpaperapp.viewmodel.PexelsViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), CategoriesAdapter.OnItemClickedListener {

    private var TAG = "HOME_FRAGMENT"
    lateinit var pexelsViewModel: PexelsViewModel

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

        //get categories
        Log.d(TAG, pexelsViewModel.categories[0].name)

        
        //categories recycler view instantiation
        category_recyclerview.adapter =
            CategoriesAdapter(pexelsViewModel.categories, view.context, this)
        category_recyclerview.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        category_recyclerview.setHasFixedSize(true)

        //trending images recyclerview instantiation
    }

    override fun onCategoryItemCLicked(category: Category) {
        TODO("Not yet implemented")
    }

}
