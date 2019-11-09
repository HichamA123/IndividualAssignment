package com.example.individualassignment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Screenshot (
    var id: Int,
    var image: String,
    var hidden: Boolean
): Parcelable {

}