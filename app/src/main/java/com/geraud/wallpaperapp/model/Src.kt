package com.geraud.wallpaperapp.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Src(
    @SerializedName("landscape")
    var landscape: String,
    @SerializedName("large")
    var large: String,
    @SerializedName("large2x")
    var large2x: String,
    @SerializedName("medium")
    var medium: String,
    @SerializedName("original")
    var original: String,
    @SerializedName("portrait")
    var portrait: String,
    @SerializedName("small")
    var small: String,
    @SerializedName("tiny")
    var tiny: String
): Serializable