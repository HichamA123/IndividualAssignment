package com.example.individualassignment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Platform (
    var platform: Platform?,
    var id: Int,
    var name: String
): Parcelable

