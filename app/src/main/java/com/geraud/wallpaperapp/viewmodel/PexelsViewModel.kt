package com.geraud.wallpaperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.geraud.wallpaperapp.api.PexelsRepository
import com.geraud.wallpaperapp.model.Category
import com.geraud.wallpaperapp.model.TrendingPhotos

class PexelsViewModel : ViewModel() {

    private val _page_number: MutableLiveData<Int> = MutableLiveData()

    //getting the trending photos on change of page number
    var trendingPhotos: LiveData<TrendingPhotos> = Transformations
        .switchMap(_page_number) {
            PexelsRepository.getTrendingPhotos(it)
        }

    //setting the page number
    fun setPageNumber(page_number: Int) {
        if (_page_number.value == page_number)
            return

        _page_number.value = page_number
    }

    //get categories from repository
    val categories: List<Category> = PexelsRepository.getCategories()





    fun cancelJobs() {
        PexelsRepository.cancelJobs()
    }

}