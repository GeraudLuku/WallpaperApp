package com.geraud.wallpaperapp.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photo(
    @SerializedName("height")
    var height: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("photographer")
    var photographer: String,
    @SerializedName("photographer_id")
    var photographerId: Int,
    @SerializedName("photographer_url")
    var photographerUrl: String,
    @SerializedName("src")
    var src: Src,
    @SerializedName("url")
    var url: String,
    @SerializedName("width")
    var width: Int
):Serializable