package com.geraud.wallpaperapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    const val BASE_URL = "https://api.pexels.com/v1/"

    val retrofitbuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val pexelsApiService: PexelsApiService by lazy {
        retrofitbuilder
            .build()
            .create(PexelsApiService::class.java)
    }
}