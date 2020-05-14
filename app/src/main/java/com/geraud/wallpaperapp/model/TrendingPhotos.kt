package com.geraud.wallpaperapp.model


import com.google.gson.annotations.SerializedName

data class TrendingPhotos(
    @SerializedName("next_page")
    var nextPage: String,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("photos")
    var photos: ArrayList<Photo>
)