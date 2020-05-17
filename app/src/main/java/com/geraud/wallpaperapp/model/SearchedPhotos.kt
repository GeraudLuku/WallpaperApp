package com.geraud.wallpaperapp.model

data class SearchedPhotos(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: ArrayList<PhotoX>,
    val total_results: Int
)