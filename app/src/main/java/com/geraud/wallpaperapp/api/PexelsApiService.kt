package com.geraud.wallpaperapp.api

import androidx.lifecycle.LiveData
import com.geraud.wallpaperapp.model.TrendingPhotos
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "563492ad6f9170000100000176c2c175fccd4ef49048d87c28263ebf"

interface PexelsApiService {

    //get trending pictures
    @Headers("Authorization : 563492ad6f9170000100000176c2c175fccd4ef49048d87c28263ebf")
    @GET("curated?per_page=15")
    suspend fun getTrendingPhotos(
        @Query("page") page: Int
    ): LiveData<TrendingPhotos>


}