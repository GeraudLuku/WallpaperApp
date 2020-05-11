package com.geraud.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import com.geraud.wallpaperapp.api.PexelsRepository
import com.geraud.wallpaperapp.model.Category

class PexelsViewModel : ViewModel() {

    //get categories from repository
    val categories: List<Category> = PexelsRepository.getCategories()

}