package com.example.individualassignment.model

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "game_table")
data class Game (
    @PrimaryKey
    var id: Int,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var released: String,

    @ColumnInfo
    var background_image: String,

    @ColumnInfo
    var rating: Int,

    @ColumnInfo
    var platforms: ArrayList<PlatformArray>,


    //data from detailed game
    var background_image_additional: String?,

    var description_raw: String?,

    var website: String?,

    var selected: Boolean = false
): Parcelable {
    //functions to get for example the YT video or game posters
}