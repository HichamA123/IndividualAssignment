package com.example.individualassignment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game (
    var id: Int,
    var name: String,
    var released: String,
    var background_image: String,
    var background_image_additional: String,
    var description_raw: String,
    var rating: Int,
    var platforms: ArrayList<Platform>,
    var selected: Boolean = false
): Parcelable {
    //functions to get for example the YT video or game posters
}