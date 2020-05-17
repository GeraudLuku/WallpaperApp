package com.geraud.wallpaperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.geraud.wallpaperapp.api.PexelsRepository
import com.geraud.wallpaperapp.model.Category
import com.geraud.wallpaperapp.model.SearchQuery
import com.geraud.wallpaperapp.model.SearchedPhotos
import com.geraud.wallpaperapp.model.TrendingPhotos

class PexelsViewModel : ViewModel() {

    private val _trending_page_number: MutableLiveData<Int> = MutableLiveData()

    //getting the trending photos on change of page number
    var trendingPhotos: LiveData<TrendingPhotos> = Transformations
        .switchMap(_trending_page_number) {
            PexelsRepository.getTrendingPhotos(it)
        }

    //setting the page number
    fun setPageNumber(page_number: Int) {
        if (_trending_page_number.value == page_number)
            return

        _trending_page_number.value = page_number
    }

    //get categories from repository
    val categories: List<Category> = PexelsRepository.getCategories()

    //get searched photos
    private val _search_query_text: MutableLiveData<String> = MutableLiveData()
    private val _search_query: MutableLiveData<SearchQuery> = MutableLiveData()

    var searchedPhotos: LiveData<SearchedPhotos> = Transformations
        .switchMap(_search_query) {
            PexelsRepository.searchPhotos(it.query, it.page_number)
        }

    //setting the search object
    fun setSearchQuery(page_number: Int, query: String = _search_query_text.toString()) {
        val data = SearchQuery(page_number, query)

        if (_search_query.value == data)
            return

        _search_query_text.value = query
        _search_query.value = data
    }


    fun cancelJobs() {
        PexelsRepository.cancelJobs()
    }

}