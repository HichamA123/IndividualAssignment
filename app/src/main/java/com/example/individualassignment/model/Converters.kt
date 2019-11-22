package com.example.individualassignment.model

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun arrayListToString(lists: ArrayList<PlatformArray>?): String? {
        var platforms = ""
        for (list in lists!!.iterator()) {
            platforms += list.platform.id.toString() + "," + list.platform.name + "."
        }

        platforms = platforms.substring(0, platforms.length - 1)
        return platforms
    }

    @TypeConverter
    fun fromStringToArrayList(platforms: String): ArrayList<PlatformArray>? {
        var platFormArray = arrayListOf<PlatformArray>()

        for (platform in platforms.split(".")) {
            val platformSplit = platform.split(",")
            platFormArray.add(PlatformArray(Platform(platformSplit[0].toInt(), platformSplit[1])))
        }

        return platFormArray
    }
}