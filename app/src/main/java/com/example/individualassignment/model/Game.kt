package com.example.individualassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game (
    var id: Int,
    var name: String,
    var released: String,
    var background_image: String,
    var rating: Int
): Parcelable {
    //functions to get for example the YT video or game posters
}