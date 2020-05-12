package com.geraud.wallpaperapp.api

import com.geraud.wallpaperapp.model.Category

object PexelsRepository {

    fun getCategories(): List<Category> {
        return listOf<Category>(
            Category(
                "https://images.pexels.com/photos/545008/pexels-photo-545008.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Street Art"
            ),
            Category(
                "https://images.pexels.com/photos/704320/pexels-photo-704320.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Wild Life"
            ),
            Category(
                "https://images.pexels.com/photos/34950/pexels-photo.jpg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Nature"
            ),
            Category(
                "https://images.pexels.com/photos/466685/pexels-photo-466685.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "City"
            ),
            Category(
                "https://images.pexels.com/photos/1434819/pexels-photo-1434819.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                "Motivation"
            ),
            Category(
                "https://images.pexels.com/photos/2116475/pexels-photo-2116475.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Bikes"
            ),
            Category(
                "https://images.pexels.com/photos/1149137/pexels-photo-1149137.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                "Cars"
            )
        )
    }



}